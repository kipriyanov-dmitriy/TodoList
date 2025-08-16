# 🔍 Диагностика проблем с keystore

## Проблема
Gradle все еще ищет keystore по пути `/home/runner/work/TodoList/TodoList/app/release-keystore.jks`

## Что добавлено для диагностики

### 1. В `build.gradle.kts`
- Подробное логирование всех переменных окружения
- Проверка существования файла keystore
- Вывод абсолютных путей

### 2. В `build-and-version.yml`
- Логирование текущей директории
- Проверка содержимого директории до и после декодирования
- Проверка существования файла keystore
- Логирование всех переменных окружения

### 3. В `test-keystore.yml`
- Аналогичное логирование для тестирования

## План диагностики

### Шаг 1: Запустить Test Keystore
1. Перейдите в **Actions** → **Test Keystore**
2. Запустите workflow
3. Проверьте логи на наличие:
   - `Current directory: /home/runner/work/TodoList/TodoList`
   - `Keystore decoded to: release-keystore.jks`
   - `Keystore file exists: YES`

### Шаг 2: Проверить основной workflow
1. Сделайте commit в main
2. Запустится `build-and-version.yml`
3. В логах ищите:
   - `=== KEYSTORE DECODING DEBUG ===`
   - `=== BUILD DEBUG ===`
   - `=== SIGNING CONFIG DEBUG ===`

### Шаг 3: Анализ логов
Ищите в логах:

#### В workflow:
```
=== KEYSTORE DECODING DEBUG ===
Current directory: /home/runner/work/TodoList/TodoList
Keystore decoded to: release-keystore.jks
Keystore file exists: YES
```

#### В Gradle:
```
=== SIGNING CONFIG DEBUG ===
RELEASE_KEYSTORE_PATH: release-keystore.jks
Using CI signing config with keystore: release-keystore.jks
Keystore file exists: true
Keystore file absolute path: /home/runner/work/TodoList/TodoList/release-keystore.jks
```

## 🔧 Возможные проблемы

### 1. Неправильный путь в Gradle
Если в логах Gradle все еще показывается путь с `/app/`, значит где-то в коде есть хардкод.

### 2. Кэширование
Gradle может кэшировать старые настройки. Попробуйте:
```bash
./gradlew clean
./gradlew assembleRelease
```

### 3. Неправильные переменные окружения
Проверьте, что все секреты настроены правильно:
- `SIGNING_KEYSTORE`
- `SIGNING_KEY_ALIAS`
- `SIGNING_KEY_PASSWORD`
- `SIGNING_STORE_PASSWORD`

## Что должно быть в логах

### Успешная сборка:
```
=== KEYSTORE DECODING DEBUG ===
Current directory: /home/runner/work/TodoList/TodoList
Keystore decoded to: release-keystore.jks
Keystore file exists: YES

=== BUILD DEBUG ===
Building with keystore path: release-keystore.jks
Keystore file exists: YES

=== SIGNING CONFIG DEBUG ===
RELEASE_KEYSTORE_PATH: release-keystore.jks
Using CI signing config with keystore: release-keystore.jks
Keystore file exists: true
```

### Если что-то не так:
1. Проверьте все секреты
2. Пересоздайте keystore в base64
3. Очистите кэш GitHub Actions
