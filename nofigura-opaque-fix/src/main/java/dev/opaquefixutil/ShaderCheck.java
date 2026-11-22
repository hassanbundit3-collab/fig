package dev.opaquefix;

import java.lang.reflect.Method;

/**
 * ตรวจว่ากำลังใช้เชดเดอร์ (Iris) อยู่ไหม โดยใช้ reflection
 * จึงไม่ต้องมี Iris ตอนคอมไพล์ และไม่พังถ้าไม่ได้ติดตั้ง Iris
 *
 * System properties:
 *   -Dnofigura.opaquefix.disable=true  ปิดตัวแก้ทั้งหมด
 *   -Dnofigura.opaquefix.always=true   แก้ตลอด แม้ไม่ได้เปิดเชดเดอร์
 */
public final class ShaderCheck {
    private static final boolean DISABLED = Boolean.getBoolean("nofigura.opaquefix.disable");
    private static final boolean ALWAYS = Boolean.getBoolean("nofigura.opaquefix.always");

    private static boolean resolved;
    private static Method getInstance;
    private static Method isShaderPackInUse;

    private ShaderCheck() {}

    public static boolean shouldForceOpaque() {
        if (DISABLED) return false;
        if (ALWAYS) return true;
        return shadersActive();
    }

    private static boolean shadersActive() {
        if (!resolved) resolve();
        if (getInstance == null || isShaderPackInUse == null) return false;
        try {
            Object api = getInstance.invoke(null);
            return (Boolean) isShaderPackInUse.invoke(api);
        } catch (Throwable t) {
            return false;
        }
    }

    private static synchronized void resolve() {
        if (resolved) return;
        try {
            Class<?> apiClass = Class.forName(
                    "net.irisshaders.iris.api.v0.IrisApi", false, ShaderCheck.class.getClassLoader());
            getInstance = apiClass.getMethod("getInstance");
            isShaderPackInUse = apiClass.getMethod("isShaderPackInUse");
        } catch (Throwable t) {
            getInstance = null;
            isShaderPackInUse = null;
        }
        resolved = true;
    }
}
