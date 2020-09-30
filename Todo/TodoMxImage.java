public class JFoo {
  private long ptr; // 指向底层对象的指针，伪装成Java的long

  public JFoo(Args args) {
    ptr = createCFoo(args);
  }

  public void finalize() { // 在finalizer里释放CFoo
    long tmpPtr = this.ptr;
    this.ptr = 0;  // 将ptr置0避免CFoo销毁后JFoo被复活的话碰到悬空指针
    freeCFoo(tmpPtr);
  }

  private static native long createCFoo(Args args);
  private static native long freeCFoo(long ptr);
}