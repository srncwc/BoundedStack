/**
 * 
 * BoundedStack - ADT เก็บ game checkpoint เป็น String
 * ค่านามธรรม (A): ลำดับของ checkpoint ที่ถูกบันทึก โดย checkpoint ที่บันทึกล่าสุดอยู่บนสุด 
 * 
 * ตัวอย่างการใช้งาน
 * save "A", "B", "C"  =>  [A, B, C]
 *                                ^ 
 *                               top
 */
public class BoundedStack{
    private final String[] checkpoints;
    private int size;

    // ===== representation =====


    // Abstraction Function:
    //   AF(checkpoint , size) =  BoundedStack ที่ประกอบด้วย checkpoints[0] ถึง checkpoints[size - 1]
    //   เรียงจาก checkpoint เก่าสุดไปล่าสุด
    //   โดย checkpoints[size - 1] คือ top ของ stack
    //   และ checkpoints.length คือจำนวน save สูงสุดของ stack นี้


    // Representation Invariant:
    //   checkpoints != null
    //   checkpoints.length > 0
    //   0 <= size <= checkpoints.length
    //   checkpoints[0 ... size-1] != null
    //   checkpoints[size ... checkpoints.length-1] == null

    // Safety from rep exposure:
    //   checkpoints เป็น private final และไม่มี method ที่คืน array ภายในออกไปโดยตรง

    private void checkRep() {
        assert checkpoints != null : "checkpoint must be not null" ;
        assert checkpoints.length > 0 : "capacity must be positive" ;
        assert size >= 0 && size <= checkpoints.length : "size must be within capacity" ;
        
        for (int i = 0; i < size; i++) {
        assert checkpoints[i] != null : "saved checkpoint must not be null";
        }
         
        for (int i = size; i < checkpoints.length; i++) {
        assert checkpoints[i] == null : "unused slot must be null";
        }
    }

    // ===== Creator =====
     /**
     * สร้าง BoundedStack ว่างที่เก็บ checkpoint ได้สูงสุด maxSaves รายการ
     *
     * Precondition:
     *      maxSaves > 0
     *
     * Postcondition:
     *      stack ว่าง
     *      size() == 0 
     *      isEmpty() == true
     *      isFull() == false
     * 
     * 
     * @param maxSaves จำนวน checkpoint สูงสุด
     * @throws IllegalArgumentException ถ้า maxSaves <= 0
 */

    public BoundedStack(int maxSaves){
        if (maxSaves <= 0) throw new IllegalArgumentException();

        this.checkpoints = new String[maxSaves];
        this.size = 0;

        checkRep();
    }
 
    // ===== Mutators =====
    /**
     * บันทึก checkpoint ใหม่ไว้บนสุดของ stack
     *
     * Precondition:
     *      checkpoint != null
     *      stack ต้องไม่เต็ม
     *
     * Postcondition:
     *      checkpoint กลายเป็น top
     *      size เพิ่มขึ้น 1
     *
     * @param checkpoint checkpoint ที่ต้องการบันทึก
     * @throws IllegalArgumentException ถ้า checkpoint เป็น null
     * @throws IllegalStateException ถ้า stack เต็ม
    */
    public void saveCheckpoint(String checkpoint) {
    }
    
   

    // ===== Observers =====
    /**
     * นำ checkpoint ล่าสุดออกจาก stack และคืน checkpoint นั้น
     *
     * Precondition:
     *      stack ต้องไม่ว่าง
     *
     * Postcondition:
     *      checkpoint ที่เป็น top ถูกนำออก
     *      size ลดลง 1
     *
     * @return checkpoint ที่ถูกนำออก
     * @throws IllegalStateException ถ้า stack ว่าง
    */
    public String loadLastCheckpoint() {
        return null;
    }


    /**
     * คืน checkpoint ล่าสุดโดยไม่เปลี่ยน stack
     *
     * Precondition:
     *      stack ต้องไม่ว่าง
     *
     * Postcondition:
     *      stack ไม่เปลี่ยนแปลง
     *      size เท่าเดิม
     *
     * @return checkpoint ที่อยู่บนสุด
     * @throws IllegalStateException ถ้า stack ว่าง
    */
    public String peekLatestCheckpoint() {
        return null;
    }

    /**
     * คืนจำนวน checkpoint ที่อยู่ใน stack
     *
     * Postcondition:
     *      stack ไม่เปลี่ยนแปลง
     *
     * @return จำนวน checkpoint ปัจจุบัน
    */
    public int size() {
        return 0;
    }

    /**
     * ตรวจว่า stack ว่างหรือไม่
     *
     * Postcondition:
     *      stack ไม่เปลี่ยนแปลง
     *
     * @return true ถ้าไม่มี checkpoint, ไม่งั้น false
    */
    public boolean isEmpty() {
        return false;
    }

    /**
     * ตรวจว่า stack เต็มหรือไม่
     *
     * Postcondition:
     *      stack ไม่เปลี่ยนแปลง
     *
     * @return true ถ้าจำนวน checkpoint เท่ากับ capacity, ไม่งั้น false
    */
    public boolean isFull() {
        return false;
    }

    // ===== Producer =====    
    /**
     * สร้าง BoundedStack ใหม่ที่มี checkpoint
     * และ capacity เหมือน stack ปัจจุบัน
     *
     * Postcondition:
     *   stack เดิมไม่เปลี่ยน
     *   stack ใหม่มี size เท่ากับ stack เดิม
     *   stack ใหม่มี checkpoint ตามลำดับเดียวกัน
     *   การแก้ stack ใหม่ไม่กระทบ stack เดิม
     *
     * @return BoundedStack ใหม่ที่เป็นสำเนาของ stack นี้
     */

    public BoundedStack copy(){
        return null ;
    }
}