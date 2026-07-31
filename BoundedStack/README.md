# BoundedStack

`BoundedStack` เป็น Abstract Data Type (ADT) สำหรับจัดเก็บ **Game Checkpoint** โดยมีจำนวน Save สูงสุดตาม `capacity` ที่กำหนดตอนสร้าง 

Checkpoint ทำงานในรูปแบบ **LIFO (Last In, First Out)** Checkpoint ที่ Save ล่าสุดจะอยู่บนสุด (`top`) และเป็นตัวแรกที่ถูก Load ออก 

## Example 

กำหนดให้ Stack มี `capacity = 3` 
```
 saveCheckpoint("A")
 saveCheckpoint("B") 
 saveCheckpoint("C") 
 
 // Stack: [A, B, C] 
 // C is top of Stack
 
 peekLatestCheckpoint() -> "C"
``` 

หลังจาก Load:

```
loadLastCheckpoint() -> "C"

// Stack: [A, B]
// B is top of Stack
```

## Design Decisions

### Checkpoint
Checkpoint ถูกเก็บเป็น `String`
เลือกใช้ `String` เพื่อให้โครงสร้างของ ADT ทำได้ง่าย, เร็ว และเน้นการออกแบบพฤติกรรมของ `BoundedStack` เป็นหลัก 

### Capacity
ความจุสูงสุด (`capacity`) ถูกกำหนดตอนสร้าง BoundedStack

```
BoundedStack saves = new BoundedStack(3);
```

กำหนดให้ `capacity > 0` ดังนั้น `capacity = 0` หรือ `capacity < 0`  ถือว่าไม่ถูกต้อง และจะเกิด `IllegalArgumentException`

### Full Stack

เมื่อ Stack เต็ม จะไม่สามารถ Save checkpoint เพิ่มได้ และจะเกิด  `IllegalStateException`

### Empty Stack

เมื่อ Stack ว่าง จะไม่สามารถ Load หรือ Peek checkpoint ได้ และจะเกิด `IllegalStateException`

### Null Checkpoint

ไม่อนุญาตให้ `null` เป็น checkpoint โดย `saveCheckpoint(null)` จะทำให้เกิด `IllegalArgumentException`

### Mutability

`BoundedStack` เป็น **Mutable ADT** โดย `saveCheckpoint()` และ  
`loadLastCheckpoint()` สามารถเปลี่ยน state ของ BoundedStack เดิมได้

# Operations

## Creator

### `BoundedStack(int maxSaves)`

สร้าง BoundedStack ว่างที่มีความจุสูงสุดตาม `maxSaves`

**Precondition** - `maxSaves > 0`

**Postcondition** - `size() == 0` - `isEmpty() == true` -  
`isFull() == false`

**Error** - `maxSaves <= 0` -> `IllegalArgumentException`

## Producer

### `BoundedStack copy()`

สร้าง BoundedStack ใหม่ที่มี checkpoint, ลำดับ และ capacity เหมือนกับ Stack  
ปัจจุบัน

**Postcondition** - Stack เดิมไม่เปลี่ยน - Stack ใหม่มี `size` เท่ากับ Stack  
เดิม - Checkpoint มีลำดับเหมือนเดิม - การแก้ Stack ใหม่ไม่กระทบ Stack เดิม  
และกลับกัน

## Mutators

### `void saveCheckpoint(String checkpoint)`

เพิ่ม checkpoint ใหม่ไว้บนสุดของ Stack

**Precondition** - `checkpoint != null` - Stack ต้องไม่เต็ม

**Postcondition** - checkpoint ใหม่กลายเป็น `top` - `size` เพิ่มขึ้น 1

**Error** - `checkpoint == null` -> `IllegalArgumentException` - Stack  
เต็ม -> `IllegalStateException`

### `String loadLastCheckpoint()`

นำ checkpoint ล่าสุดออกจาก Stack และคืน checkpoint นั้น

**Precondition** - Stack ต้องไม่ว่าง

**Postcondition** - checkpoint ที่เป็น `top` ถูกนำออก - `size` ลดลง 1

**Error** - Stack ว่าง -> `IllegalStateException`

## Observers

### `String peekLatestCheckpoint()`

คืน checkpoint ที่อยู่บนสุดโดยไม่เปลี่ยน Stack

**Postcondition** - Stack ไม่เปลี่ยน - `size` เท่าเดิม

**Error** - Stack ว่าง -> `IllegalStateException`

### `int size()`

คืนจำนวน checkpoint ที่อยู่ใน Stack ปัจจุบัน

### `boolean isEmpty()`

คืน `true` เมื่อ Stack ไม่มี checkpoint

### `boolean isFull()`

คืน `true` เมื่อจำนวน checkpoint เท่ากับ capacity

# Representation

ภายในใช้:

```
private final String[] checkpoints;
private int size;
```

`checkpoints` ใช้เก็บ checkpoint และ `size` ใช้ระบุจำนวน checkpoint ที่มีอยู่ใน  
Stack ปัจจุบัน

ตัวอย่าง:

```
capacity = 3
size = 2

[A, B, null]
```

ตำแหน่งตั้งแต่ `0` ถึง `size - 1` คือ checkpoint ที่ถูกใช้งาน ส่วนตำแหน่งตั้งแต่ `size`  
เป็นต้นไปเป็นพื้นที่ว่าง

Representation ทั้งหมดเป็น `private` และไม่มีการคืน array ภายในให้ Client  
โดยตรง เพื่อป้องกัน **Rep Exposure**

# Testing

- Empty Stack
- Stack ที่มี checkpoint เดียว
- Almost Full
- Full Stack
- Save เมื่อ Stack เต็ม
- Load เมื่อ Stack ว่าง
- Peek เมื่อ Stack ว่าง
- Capacity = 1
- Capacity = 0
- Negative Capacity
- Null Checkpoint
- LIFO Order
- `peekLatestCheckpoint()` ต้องไม่เปลี่ยน state
- State ต้องไม่เปลี่ยนหลัง operation ที่ throw exception
- Independence ของ `copy()`
- `copy()` ต้องมี capacity เท่ากับ Stack เดิม

# Development Process

Specification-first ร่วมกับ Test-Driven Development (TDD)

```
Specification
    ↓
Representation + AF/RI -- now 
    ↓
Test
    ↓
Implementation
    ↓
Boundary / Error Test
    ↓
Refactor
```

<!-- NAME: Saran Chaiwicha -->
<!-- NISIT ID: 6821651779 -->
<!-- SECTION: 800 -->