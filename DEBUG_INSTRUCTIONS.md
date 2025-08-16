# 🔍 Диагностика проблем с keystore

## Проблема
Gradle ищет keystore по пути `/home/runner/work/TodoList/TodoList/app/release-keystore.jks`

## Решение
**Изменили подход:** Теперь keystore декодируется в папку `app/` как того требует Gradle.

## Что добавлено для диагностики

### 1. В `build.gradle.kts`
- Подробное логирование всех переменных окружения
- Проверка существования файла keystore
- Вывод абсолютных путей

### 2. В `build-and-version.yml`
- Логирование текущей директории
- Проверка содержимого директории до и после декодирования
- Проверка содержимого папки `app/`
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
   - `Keystore decoded to: app/release-keystore.jks`
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
Keystore decoded to: app/release-keystore.jks
Keystore file exists: YES
App folder contents:
-rw-r--r-- 1 runner docker 12345 release-keystore.jks
```

#### В Gradle:
```
=== SIGNING CONFIG DEBUG ===
RELEASE_KEYSTORE_PATH: app/release-keystore.jks
Using CI signing config with keystore: app/release-keystore.jks
Keystore file exists: true
Keystore file absolute path: /home/runner/work/TodoList/TodoList/app/release-keystore.jks
```

## 🔧 Возможные проблемы

### 1. Неправильный путь в Gradle
Теперь keystore должен находиться в папке `app/` как того требует Gradle.

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

## 📋 Что должно быть в логах

### Успешная сборка:
```
=== KEYSTORE DECODING DEBUG ===
Current directory: /home/runner/work/TodoList/TodoList
Keystore decoded to: app/release-keystore.jks
Keystore file exists: YES
App folder contents:
-rw-r--r-- 1 runner docker 12345 release-keystore.jks

=== BUILD DEBUG ===
Building with keystore path: app/release-keystore.jks
Keystore file exists: YES

=== SIGNING CONFIG DEBUG ===
RELEASE_KEYSTORE_PATH: app/release-keystore.jks
Using CI signing config with keystore: app/release-keystore.jks
Keystore file exists: true
```

### Если что-то не так:
1. Проверьте все секреты
2. Пересоздайте keystore в base64
3. Очистите кэш GitHub Actions

## 📂 Новые пути к keystore:
- **Локально:** `my-release-key.jks` (в корневой папке проекта)
- **CI/CD:** `app/release-keystore.jks` (декодируется в папку app/)
