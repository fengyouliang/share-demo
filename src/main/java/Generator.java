import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Youliang Feng
 */
public class Generator {
    public static void main(String[] args) throws Exception {
        //读取
        ClassReader classReader = new ClassReader("Base");
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        //处理
        ClassVisitor classVisitor = new MyClassVisitor(classWriter);
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);
        byte[] data = classWriter.toByteArray();
        //输出
        File f = new File("D:\\code\\ShareDemo\\target\\classes\\Base.class");
        FileOutputStream fileOutputStream = new FileOutputStream(f);
        fileOutputStream.write(data);
        fileOutputStream.close();
        System.out.println("now generator cc success!!!!!");
    }
}