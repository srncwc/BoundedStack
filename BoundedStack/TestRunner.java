public class TestRunner {

    static int pass = 0;
    static int fail = 0;

    static void check(String name, boolean ok) {
        if (ok) {
            pass++;
            System.out.println("[PASS] " + name);
        } else {
            fail++;
            System.out.println("[FAIL] " + name);
        }
    }

    public static void main(String[] args) {
        testCreator();
        
        System.out.println("====================");
        System.out.printf("PASS %d / FAIL %d%n", pass, fail);
        System.out.println("====================");
    }

    private static void testCreator(){
        BoundedStack stack = new BoundedStack(3) ; 
        check("new stack size is 0", stack.size() == 0) ;
        check("new stack is empty", stack.isEmpty()) ;
        check("new stack is not full", !stack.isFull()) ; 
    }

    private static void testSave(){
        // BoundedStack stack = new BoundedStack(3) ; 
        // stack.saveCheckpoint("first_check") ;
        // check("save: size 1", stack.size() == 1) ;

    }
    private static void testLoad(){
        
    }
    private static void testObservers(){

    }
    private static void testProducer(){

    }
    private static void testRepExposure(){

    }

}