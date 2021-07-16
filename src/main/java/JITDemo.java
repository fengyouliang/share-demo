/**
 * @author Youliang Feng
 *
 * -Xint：参数强制虚拟机处于"解释模式"此时编译器不工作
 * -Xcomp：参数强制虚拟机处于"编译模式"此时解释器不工作
 * -server：参数使C1不工作
 * -client：参数使C2不工作
 * -XX:+PrintCompilation 来打印即时编译情况
 *
 * The compiler in the server VM now provides correct stack backtraces for all "cold" built-in exceptions.
 * For performance purposes, when such an exception is thrown a few times, the method may be recompiled.
 * After recompilation, the compiler may choose a faster tactic using preallocated exceptions that do not provide a stack trace.
 * To disable completely the use of preallocated exceptions, use this new flag: -XX:-OmitStackTraceInFastThrow.
 *
 * https://juejin.cn/post/6844904166943358989
 */
public class JITDemo {

    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            System.out.println("times:" + i + " , result:" + testExceptionTrunc());
        }
    }

    // watch JITDemo testExceptionTrunc '{params,returnObj,throwExp}'  -n 5  -x 3
    public static boolean testExceptionTrunc() {
        try {
            // 人工构造异常抛出的场景
            ((Object) null).getClass();
        } catch (Exception e) {
            if (e.getStackTrace().length == 0) {  // 即时编译导致堆栈丢失
                try {
                    // 堆栈消失的时候当前线程休眠5秒，便于观察
                    Thread.sleep(5000);
                } catch (InterruptedException interruptedException) {
                    // do nothing
                }
                return true;
            }
        }
        return false;
    }
}