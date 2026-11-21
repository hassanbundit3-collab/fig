# NoFigura Opaque Shader Fix (Fabric, Minecraft 26.3)

แก้ปัญหาโมเดลอวตาร์ของ NoFigura/Figura โปร่งใสเมื่อเปิดเชดเดอร์ (Iris)

## วิธี build
1. ติดตั้ง JDK 25
2. คัดลอก `nofigura-0_1_6_9_26_3-fabric-mc.jar` ไปไว้ใน `libs/`
3. รัน `./gradlew build`  (ถ้าไม่มี wrapper ให้ใช้ Gradle 9.5+ รัน `gradle wrapper` ก่อน)
4. ไฟล์ผลลัพธ์อยู่ที่ `build/libs/nofigura-opaque-fix-1.0.0.jar` ใส่ในโฟลเดอร์ mods ได้เลย
   (ไม่ใช่ไฟล์ -sources)

ถ้า Gradle ฟ้องเวอร์ชัน loom/loader ให้เปิด https://fabricmc.net/develop เลือก 26.3
แล้วแก้ค่าใน `gradle.properties` ให้ตรง

## ตัวเลือก (JVM arguments)
- `-Dnofigura.opaquefix.always=true`  บังคับแก้ตลอด แม้ไม่ได้เปิดเชดเดอร์
- `-Dnofigura.opaquefix.disable=true` ปิดตัวแก้
