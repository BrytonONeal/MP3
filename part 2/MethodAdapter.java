import java.io.IOException;

import org.objectweb.asm.*;

public class MethodAdapter extends MethodVisitor implements Opcodes {
    public MethodAdapter(MethodVisitor mv) {
        super(ASM5,mv);
    }
    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
    	switch (opcode) {
        	case INVOKEVIRTUAL:
        		if(isThreadClass(owner)&&name.equals("start")&&desc.equals("()V")) {
				}
        	default: mv.visitMethodInsn(opcode, owner, name, desc,itf);
    	}

    }
    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
	    switch (opcode) {
		    case GETSTATIC:
		    	mv.visitLdcInsn(owner);
		    	mv.visitLdcInsn(name);
		    	mv.visitLdcInsn(1);
		    	mv.visitLdcInsn(0);
		    	mv.visitMethodInsn(INVOKESTATIC, "Log", "logFieldAcc", "(Ljava/lang/Object;Ljava/lang/String;ZZ)V",false);
		    	break;
		    case PUTSTATIC:
		    	mv.visitLdcInsn(owner);
		    	mv.visitLdcInsn(name);
		    	mv.visitLdcInsn(1);
		    	mv.visitLdcInsn(1);
		    	mv.visitMethodInsn(INVOKESTATIC, "Log", "logFieldAcc", "(Ljava/lang/Object;Ljava/lang/String;ZZ)V",false);
		    	break;
		    case GETFIELD:
		    	mv.visitLdcInsn(owner);
		    	mv.visitLdcInsn(name);
		    	mv.visitLdcInsn(0);
		    	mv.visitLdcInsn(0);
		    	mv.visitMethodInsn(INVOKESTATIC, "Log", "logFieldAcc", "(Ljava/lang/Object;Ljava/lang/String;ZZ)V",false);
		    	break;
		    case PUTFIELD:
		    	mv.visitLdcInsn(owner);
		    	mv.visitLdcInsn(name);
		    	mv.visitLdcInsn(0);
		    	mv.visitLdcInsn(1);
		    	mv.visitMethodInsn(INVOKESTATIC, "Log", "logFieldAcc", "(Ljava/lang/Object;Ljava/lang/String;ZZ)V",false);
		    	break;
		    default: break;
	    }
    mv.visitFieldInsn(opcode, owner, name, desc);
    } 
    
    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
    mv.visitMaxs(10, 10); // set X and Y to a proper value
    }

    @Override
    public void visitInsn(int opcode) {
    	switch (opcode) {
		    case AALOAD:case BALOAD:case CALOAD:case SALOAD:case IALOAD:case FALOAD:case
		    DALOAD:case LALOAD:
		    	mv.visitInsn(DUP2);
		    	mv.visitLdcInsn(0);
		    	mv.visitMethodInsn(INVOKESTATIC, "Log", "logArrayAcc", "(Ljava/lang/Object;IZ)V",false);
			    break;
		    case AASTORE:case BASTORE:case CASTORE:case SASTORE:case IASTORE:case FASTORE:
		    	mv.visitInsn(DUP_X2);
		    	mv.visitInsn(POP);
		    	mv.visitInsn(DUP2_X1);
		    	mv.visitLdcInsn(1);
		    	mv.visitMethodInsn(INVOKESTATIC, "Log", "logArrayAcc", "(Ljava/lang/Object;IZ)V",false);
			    break;
		    case DASTORE:case LASTORE:
		    	mv.visitInsn(DUP2_X2);
		    	mv.visitInsn(POP2);
		    	mv.visitInsn(DUP2_X2);
		    	mv.visitLdcInsn(1);
		    	mv.visitMethodInsn(INVOKESTATIC, "Log", "logArrayAcc", "(Ljava/lang/Object;IZ)V",false);
			    break;
		    default:
    	}
    	mv.visitInsn(opcode);
    }

    private boolean isThreadClass(String cname)
    {
    	while(!cname.equals("java/lang/Object"))
    	{
    		if(cname.equals("java/lang/Thread"))
    			return true;

    		try {
				ClassReader cr= new ClassReader(cname);
				cname = cr.getSuperName();
			} catch (IOException e) {
				return false;
			}
    	}
    	return false;
    }
}