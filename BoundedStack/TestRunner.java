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
        // testCreator();
        // testSave();
        testLoad();
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
        BoundedStack stack = new BoundedStack(3) ; 
        stack.saveCheckpoint("first_check") ;
        check("save: size 1", stack.size() == 1) ;
        check("save: stack is not empty", !stack.isEmpty());
        check("save: latest checkpoint is first_check", stack.peekLatestCheckpoint().equals("first_check")) ;
        stack.saveCheckpoint("test_check");
        check("save: save another checkpoint and size become 2", stack.size() == 2) ; 
        check("save: lastest checkpoint become test_check", stack.peekLatestCheckpoint().equals("test_check")) ;

        // stack.saveCheckpoint("check_C");
        // check("save: stack is full", stack.isFull());

        // stack.saveCheckpoint("D");  throw
    }

    private static void testLoad(){
        BoundedStack stack = new BoundedStack(3);
        stack.saveCheckpoint("A");
        stack.saveCheckpoint("B");
        stack.saveCheckpoint("C");

        String loaded = stack.loadLastCheckpoint();
        check("load: returns top C", "C".equals(loaded));
        check("load: size decreases to 2", stack.size() == 2);
        check("load: new top is B", "B".equals(stack.peekLatestCheckpoint()));

        loaded = stack.loadLastCheckpoint();
        check("load: returns top B", "B".equals(loaded));
        check("load: size decreases to 1", stack.size() == 1);
        check("load: new top is A", "A".equals(stack.peekLatestCheckpoint()));
        
        loaded = stack.loadLastCheckpoint();
        check("load: returns top A", "A".equals(loaded));
        check("load: size decreases to 0", stack.size() == 0);
        check("load: stack is empty", stack.isEmpty());
    }
    private static void testObservers(){
        

        }
    private static void testProducer(){

    }
    private static void testRepExposure(){

    }

}