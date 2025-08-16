#!/bin/bash

# Скрипт для локальной сборки Android приложения
# Использует локальный keystore для подписи

echo "Начинаем локальную сборку..."

# Проверяем наличие keystore
if [ ! -f "app/my-release-key.jks" ]; then
    echo "Keystore файл не найден!"
    echo "Создайте keystore командой:"
    echo "keytool -genkey -v -keystore app/my-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias local_alias"
    exit 1
fi

# Очищаем предыдущую сборку
echo "Очищаем предыдущую сборку..."
./gradlew clean

# Собираем debug версию для тестирования
echo "Собираем debug версию..."
./gradlew assembleDebug

if [ $? -eq 0 ]; then
    echo "Debug сборка успешна!"
    echo "APK находится в: app/build/outputs/apk/debug/"
else
    echo "Debug сборка не удалась!"
    exit 1
fi

# Собираем release версию
echo "🔨 Собираем release версию..."
./gradlew assembleRelease

if [ $? -eq 0 ]; then
    echo "Release сборка успешна!"
    echo "APK находится в: app/build/outputs/apk/release/"
    
    # Показываем размер APK
    APK_PATH=$(find app/build/outputs/apk/release -name "*.apk" | head -n 1)
    if [ -n "$APK_PATH" ]; then
        APK_SIZE=$(du -h "$APK_PATH" | cut -f1)
        echo "📏 Размер APK: $APK_SIZE"
    fi
else
    echo "Release сборка не удалась!"
    exit 1
fi

echo "Локальная сборка завершена успешно!"
