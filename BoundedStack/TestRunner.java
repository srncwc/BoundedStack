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
        testSave();
        testLoad();
        testObservers();
        testProducer();
        testInvalidOperations();
        testBoundary();
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
        check("save: latest checkpoint is first_check", "first_check".equals(stack.peekLatestCheckpoint())) ;
        stack.saveCheckpoint("test_check");
        check("save: save another checkpoint and size become 2", stack.size() == 2) ; 
        check("save: lastest checkpoint become test_check", stack.peekLatestCheckpoint().equals("test_check")) ;

        stack.saveCheckpoint("check_C");
        check("save: stack is full", stack.isFull());

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
        BoundedStack stack = new BoundedStack(3);
        check("observer: new stack size is 0", stack.size() == 0);
        check("observer: new stack is empty", stack.isEmpty());
        check("observer: new stack is not full", !stack.isFull());

        stack.saveCheckpoint("A");
        stack.saveCheckpoint("B");

        int sizeBeforePeek = stack.size();
        check("observer: peek returns B", "B".equals(stack.peekLatestCheckpoint()));
        check("observer: peek does not change size", stack.size() == sizeBeforePeek);

        stack.saveCheckpoint("C");
        check("observer: stack is full", stack.isFull());

        }

    private static void testProducer(){
        BoundedStack original = new BoundedStack(3);
        original.saveCheckpoint("A");
        original.saveCheckpoint("B");

        BoundedStack copied = original.copy();
        check("producer: copy has same size", copied.size() == original.size());
        check("producer: copy has same top", copied.peekLatestCheckpoint().equals(original.peekLatestCheckpoint()));

        copied.saveCheckpoint("C");
        check("producer: copy keeps same capacity", copied.isFull());
        check("producer: changing copy does not change original size", original.size() == 2);
        check("producer: original top is still B", "B".equals(original.peekLatestCheckpoint()));
        

        copied.loadLastCheckpoint(); // remove C out of stack 
        check("producer: modifying copy does not affect original", original.size() == 2 && "B".equals(original.peekLatestCheckpoint()));

    }
    
    static void testInvalidOperations() {
        // capacity = 0
        boolean threw = false;
        try {
            new BoundedStack(0);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("invalid: capacity 0 throws IllegalArgumentException", threw);

        // capacity < 0
        threw = false;
        try {
            new BoundedStack(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("invalid: negative capacity throws IllegalArgumentException", threw);

        // save null
        BoundedStack stack = new BoundedStack(3);

        threw = false;
        try {
            stack.saveCheckpoint(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check("invalid: save null throws IllegalArgumentException", threw);

        // save when full
        stack.saveCheckpoint("A");
        stack.saveCheckpoint("B");
        stack.saveCheckpoint("C");

        threw = false;
        try {
            stack.saveCheckpoint("D");
        } catch (IllegalStateException e) {
            threw = true;
        }
        check("invalid: save when full throws IllegalStateException", threw);

        // load when empty
        BoundedStack emptyStack = new BoundedStack(3);

        threw = false;
        try {
            emptyStack.loadLastCheckpoint();
        } catch (IllegalStateException e) {
            threw = true;
        }
        check("invalid: load when empty throws IllegalStateException", threw);

        // peek when empty
        threw = false;
        try {
            emptyStack.peekLatestCheckpoint();
        } catch (IllegalStateException e) {
            threw = true;
        }
        check("invalid: peek when empty throws IllegalStateException", threw);
    }
    private static void testBoundary() {
        BoundedStack stack = new BoundedStack(1);
        check("boundary: capacity 1 starts empty", stack.isEmpty());
        check("boundary: capacity 1 starts not full", !stack.isFull());
        
        stack.saveCheckpoint("A");
        check("boundary: capacity 1 becomes full after one save", stack.isFull());
        check("boundary: capacity 1 size is 1", stack.size() == 1);

        String loaded = stack.loadLastCheckpoint();
        check("boundary: capacity 1 load returns A", "A".equals(loaded));
        check("boundary: capacity 1 becomes empty after load", stack.isEmpty());

        BoundedStack almostFull = new BoundedStack(3);

        almostFull.saveCheckpoint("A");
        almostFull.saveCheckpoint("B");

        check("boundary: size 2 of 3 is not full", !almostFull.isFull());
    }
}