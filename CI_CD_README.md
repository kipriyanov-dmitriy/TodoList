# CI/CD Setup для Android приложения

## Обзор

Этот проект настроен с автоматизированным CI/CD pipeline на GitHub Actions для:
- Автоматической сборки APK при каждом push в main
- Создания релизов с подписанными APK
- Отправки APK в Telegram чаты
- Автоматического версионирования

## Workflows

### 1. `build-and-version.yml` - Основной workflow (РЕКОМЕНДУЕТСЯ)
**Триггер:** Push в main branch
**Действия:**
- Сборка release APK
- Отправка APK в два Telegram чата
- Загрузка APK как артефакт
- Автоматическое увеличение версии (после успешной сборки)

### 2. `build.yml` - Только сборка
**Триггер:** Push в main branch
**Действия:**
- Сборка release APK
- Отправка APK в два Telegram чата
- Загрузка APK как артефакт

### 3. `release.yml` - Создание релиза
**Триггер:** Manual (workflow_dispatch)
**Действия:**
- Сборка release APK
- Создание GitHub Release
- Загрузка APK в релиз
- Отправка APK в Telegram чаты

### 4. `version-bump.yml` - Только версионирование (УСТАРЕЛ)
**Примечание:** Этот workflow может конфликтовать с основным. Рекомендуется использовать `build-and-version.yml`.

## Требуемые секреты

Убедитесь, что в настройках репозитория (Settings → Secrets and variables → Actions) настроены следующие секреты:

### Подпись APK
- `SIGNING_KEYSTORE` - Base64-encoded keystore файл
- `SIGNING_KEY_ALIAS` - Алиас ключа
- `SIGNING_KEY_PASSWORD` - Пароль ключа
- `SIGNING_STORE_PASSWORD` - Пароль keystore

### Telegram Bot
- `TELEGRAM_TOKEN` - Токен бота
- `TELEGRAM_CHAT1` - ID первого чата
- `TELEGRAM_CHAT2` - ID второго чата

## Настройка keystore

1. Создайте keystore для подписи:
```bash
keytool -genkey -v -keystore my-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias my-key-alias
```

2. Конвертируйте в base64:
```bash
base64 -i my-release-key.jks | tr -d '\n'
```

3. Добавьте результат в секрет `SIGNING_KEYSTORE`

## Использование

### Автоматическая сборка и версионирование (РЕКОМЕНДУЕТСЯ)
При каждом push в main branch автоматически:
- Собирается APK
- Отправляется в Telegram
- Загружается как артефакт
- Увеличивается версия

### Создание релиза
1. Перейдите в Actions → Create Release
2. Введите версию (например, 1.0.0)
3. Добавьте описание изменений
4. Запустите workflow

## Структура файлов

```
.github/
├── workflows/
│   ├── build-and-version.yml  # Основной workflow (РЕКОМЕНДУЕТСЯ)
│   ├── build.yml              # Только сборка
│   ├── release.yml            # Создание релиза
│   └── version-bump.yml       # Только версионирование (УСТАРЕЛ)
```

## Troubleshooting

### Ошибки сборки
1. **Keystore не найден:** Проверьте правильность секретов и путь к keystore
2. **Неправильный путь:** Убедитесь, что `RELEASE_KEYSTORE_PATH` установлен как `app/release-keystore.jks`
3. **Проверьте логи:** В логах должно быть "Keystore decoded successfully"

### Ошибки отправки в Telegram
1. Проверьте токен бота
2. Убедитесь, что бот добавлен в чаты
3. Проверьте ID чатов

### Проблемы с версионированием
1. **Конфликт workflows:** Используйте `build-and-version.yml` вместо отдельных workflows
2. **Права на запись:** Убедитесь, что workflow имеет права на запись в репозиторий
3. **Формат версии:** Проверьте формат версии в build.gradle.kts

## Локальная разработка

Для локальной разработки используйте локальный keystore:
```kotlin
// В build.gradle.kts уже настроено автоматическое переключение
// между CI и локальной конфигурацией
```

## Безопасность

- Никогда не коммитьте keystore файлы
- Используйте GitHub Secrets для хранения чувствительных данных
- Регулярно обновляйте токены и пароли
- Ограничьте доступ к репозиторию только необходимыми пользователями

## Миграция

Если у вас уже настроены старые workflows:

1. **Отключите** `version-bump.yml` (может конфликтовать)
2. **Используйте** `build-and-version.yml` как основной
3. **Оставьте** `build.yml` и `release.yml` для специальных случаев

## Проверка работоспособности

После настройки:

1. Сделайте push в main branch
2. Проверьте, что запустился `build-and-version.yml`
3. Убедитесь, что APK собрался и отправился в Telegram
4. Проверьте, что версия увеличилась в `build.gradle.kts`
