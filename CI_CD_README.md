# CI/CD Setup для Android приложения

## Обзор

Этот проект настроен с автоматизированным CI/CD pipeline на GitHub Actions для:
- Автоматической сборки APK при каждом push в main
- Отправки APK в Telegram чаты
- Автоматического версионирования

## Workflows

### 1. `build-and-version.yml` - Основной workflow
**Триггер:** Push в main branch
**Действия:**
- Сборка release APK
- Отправка APK в два Telegram чата
- Загрузка APK как артефакт
- Автоматическое увеличение версии (после успешной сборки)

### 2. `test-keystore.yml` - Тестирование keystore
**Триггер:** Manual (workflow_dispatch)
**Действия:**
- Декодирование keystore
- Проверка переменных окружения
- Тестирование конфигурации

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

### Автоматическая сборка и версионирование
При каждом push в main branch автоматически:
- Собирается APK
- Отправляется в Telegram
- Загружается как артефакт
- Увеличивается версия

### Тестирование keystore
1. Перейдите в Actions → Test Keystore
2. Запустите workflow
3. Проверьте логи на наличие ошибок

## Структура файлов

```
.github/
├── workflows/
│   ├── build-and-version.yml  # Основной workflow
│   └── test-keystore.yml      # Тестирование keystore
```

## Troubleshooting

### Ошибки сборки
1. **Keystore не найден:** Запустите Test Keystore workflow для диагностики
2. **Неправильный путь:** Убедитесь, что keystore декодируется в `app/release-keystore.jks`
3. **Проверьте логи:** В логах должно быть "Keystore decoded to: app/release-keystore.jks"

### Ошибки отправки в Telegram
1. Проверьте токен бота
2. Убедитесь, что бот добавлен в чаты
3. Проверьте ID чатов

### Проблемы с версионированием
1. **Права на запись:** Убедитесь, что workflow имеет права на запись в репозиторий
2. **Формат версии:** Проверьте формат версии в build.gradle.kts

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

## Проверка работоспособности

После настройки:

1. **Сначала запустите** Test Keystore workflow
2. **Проверьте логи** на наличие ошибок
3. **Сделайте push** в main branch
4. **Убедитесь**, что запустился build-and-version.yml
5. **Проверьте**, что APK собрался и отправился в Telegram
6. **Проверьте**, что версия увеличилась в build.gradle.kts

## Диагностика проблем

### Если keystore не работает:
1. Запустите Test Keystore workflow
2. Проверьте размер файла keystore (должен быть > 0)
3. Убедитесь, что все секреты настроены правильно
4. Проверьте, что keystore закодирован в base64 без переносов строк
