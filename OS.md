

### **The Purpose and Types of Operating Systems**

---

#### **Introduction**

An **Operating System (OS)** is a critical piece of software that manages computer hardware and software resources, acting as an intermediary between users and the computer. Its primary purpose is to facilitate user interaction with the hardware while ensuring efficient resource management.

---

#### **Main Functions of an Operating System**

The OS performs several essential functions:

| **Function**                  | **Description**                                                   |
|-------------------------------|-------------------------------------------------------------------|
| **Process Management**        | Manages CPU allocation to various tasks, controlling process creation, execution, and termination. |
| **Memory Management**         | Allocates and deallocates memory space to processes, ensuring efficient memory usage. |
| **File System Management**    | Organizes and controls how data is stored and retrieved on storage devices. |
| **Device Management**         | Manages communication between software applications and hardware devices.  |
| **Security & Access Control** | Protects data and ensures authorized user access through user authentication and permissions. |
| **User Interface**            | Provides a means for users to interact with the system, either via a Command-Line Interface (CLI) or a Graphical User Interface (GUI). |

---



### **Types of Operating Systems**

| **Type of OS**                      | **Description**                                                                                                                                                            | **Examples**                               |
|-------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------|
| **1. Batch Operating System**       | - Processes jobs (programs) in batches, one after the other.<br>- No user interaction during job execution.<br>- Efficient for large-scale computations and data processing. | IBM System/360, UNIVAC 1108                |
| **2. Multi-Programmed Operating System** | - Allows multiple programs to run concurrently.<br>- Shares resources (CPU, memory) among programs.<br>- Improves system utilization and reduces idle time.             | Early Unix, OS/360                        |
| **3. Multitasking Operating System**      | - Allows multiple tasks (programs) to run simultaneously.<br>- Switches between tasks quickly, providing an interactive experience.                                      | Windows, macOS, Linux                      |
| **4. Real-Time Operating System (RTOS)**   | - Guarantees task completion within strict time constraints.<br>- Prioritizes tasks based on urgency and importance.<br>- Used in critical systems like aerospace, medical devices, and industrial control. | VxWorks, QNX, FreeRTOS                     |
| **5. Distributed Operating System**         | - Manages multiple computers connected via a network.<br>- Coordinates resource sharing and communication.<br>- Scalable, fault-tolerant, and flexible.                | Google's Borg, Apache Hadoop               |
| **6. Clustered Operating System**           | - Groups multiple computers to form a single system.<br>- Improves performance, availability, and scalability.<br>- Used in high-performance computing, data centers, and cloud computing. | Beowulf cluster, Microsoft Cluster Server   |
| **7. Embedded Operating System**            | - Designed for specialized devices (e.g., IoT, consumer electronics).<br>- Optimized for resource-constrained environments, real-time performance, and low power consumption. | Android Things, FreeRTOS, VxWorks          |

### **Other Types of Operating Systems**

| **Type**                          | **Examples**                          |
|-----------------------------------|---------------------------------------|
| **Mobile Operating System**        | Android, iOS                          |
| **Server Operating System**        | Windows Server, Linux                 |
| **Mainframe Operating System**     | z/OS, z/VM                            |
| **Supercomputer Operating System**  | Cray OS, IBM AIX                      |
| **Network Operating System**       | Cisco IOS, Junos                      |

![image](https://github.com/user-attachments/assets/97ec7fd4-417f-4c03-9f12-bd0007224007)



### **Process States in an Operating System**

| **State**                | **Description**                                                                                                                                                                           |
|--------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **New State**            | A program in secondary memory is started for execution; the process is in a new state.                                                                                                 |
| **Ready State**          | After loading into main memory, a process transitions from new to ready. It waits for the CPU to execute it. Multiple processes can be in the ready state in a multiprogramming environment. |
| **Run State**            | A process that is allotted CPU for execution moves from the ready state to the run state.                                                                                              |
| **Terminate State**      | After a process finishes execution, it moves from the run state to the terminate state. The OS deletes the Process Control Block (PCB) in this state.                                    |
| **Block or Wait State**  | If a process requires an I/O operation or a blocked resource, it transitions from run to block or wait state. It moves back to the ready state once the I/O operation or resource is available. |
| **Suspend Ready State**  | If a higher-priority process needs execution while main memory is full, a lower-priority process is moved from ready to suspend ready state, freeing up space for the higher-priority process. |
| **Suspend Wait State**   | Similar to suspend ready, a lower-priority process is moved from wait to suspend wait state to free up memory for higher-priority processes. It moves back to ready once resources or memory are available. |

### **Important Notes**

- **Minimum States**: A process must pass through at least four states (new, run, ready, terminate) to be considered complete. If I/O is required, a minimum of five states is needed.
- **CPU Utilization**: Only one process can run at a time on a single CPU. With \(n\) processors, \(n\) processes can run simultaneously.
- **Memory Presence**:
  - **Secondary Memory**: New state, suspend wait state, suspend ready state
  - **Main Memory**: Ready state, run state, wait state

- **Optimal Suspension**: Moving a process from wait to suspend wait state is preferable when memory is full. This is better than suspending lower-priority ready processes, as the blocked process is already waiting for an unavailable resource.



### **Schedulers in Operating Systems**

| **Scheduler Type**         | **Description**                                                                                                                                                        |
|----------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **1. Long-Term Scheduler**  | - Also known as **Job Scheduler**.<br>- Selects processes from secondary memory and loads them into the ready queue in primary memory.<br>- Controls the degree of multiprogramming.<br>- Aims to maintain a balanced mix of I/O-bound and CPU-bound processes to optimize system performance. <br>- Improper selection can lead to CPU idleness and reduced multiprogramming. |
| **2. Short-Term Scheduler** | - Also known as **CPU Scheduler**.<br>- Chooses one job from the ready queue and dispatches it to the CPU for execution.<br>- Uses scheduling algorithms to determine job selection.<br>- Essential for minimizing wait time; poor selection can lead to “starvation” of other jobs with longer burst times. |
| **3. Medium-Term Scheduler**| - Manages processes that are swapped out of main memory.<br>- Handles processes that need I/O time by transitioning them from the running state to the waiting state.<br>- Responsible for swapping processes in and out of memory to optimize multiprogramming.<br>- Reduces the degree of multiprogramming to manage resources effectively. |

### **Key Points**

- **Long-Term Scheduler**: Influences system performance by selecting an appropriate mix of processes. A critical factor in achieving efficient multiprogramming.
- **Short-Term Scheduler**: Affects responsiveness and efficiency. Must carefully manage job selection to prevent delays and resource starvation.
- **Medium-Term Scheduler**: Facilitates resource management by controlling process swapping, enhancing the overall system performance and efficiency.





## System Calls in Operating Systems

### Definition
- **System Call**: A mechanism for user programs to request services from the operating system's kernel. It serves as an interface between user applications and the OS, allowing access to system resources.

### How System Calls Work
1. **User Space vs. Kernel Space**: User applications run in user space; system calls transition control to the kernel space.
2. **Request Process**: When a program needs OS services, it makes a system call:
   - The process issues an interrupt request, pausing its execution.
   - Control is transferred to the kernel, which performs the requested action.
   - The kernel returns results to the application, allowing it to resume execution.

### Why Are System Calls Needed?
- **File Operations**: To create, delete, read, or write files.
- **Networking**: For sending and receiving data packets.
- **Device Access**: To interact with hardware devices (e.g., printers, scanners).
- **Process Management**: To create and manage processes.

### Types of System Calls
There are commonly five types of system calls:

1. **Process Control**
   - Manages processes (e.g., create, terminate).
   - Examples: `fork()`, `exec()`, `wait()`, `exit()`.

2. **File Management**
   - Handles file operations (e.g., create, open, read, write, delete).
   - Examples: `open()`, `read()`, `write()`, `close()`.

3. **Device Management**
   - Manages device interactions (e.g., read, write, release).
   - Examples: `ioctl()`, `read()`, `write()`.

4. **Information Maintenance**
   - Maintains system information (e.g., system time, resource usage).
   - Examples: `getpid()`, `settime()`, `getsysinfo()`.

5. **Communication**
   - Facilitates inter-process communication (IPC).
   - Examples: `pipe()`, `msgget()`, `shmget()`.

### Examples of System Calls
| **Function** | **Description**                                                |
|--------------|---------------------------------------------------------------|
| `open()`     | Opens a file, allocating resources and providing a file handle. |
| `read()`     | Reads data from a file; requires file descriptor and buffer.   |
| `write()`    | Writes data to a file; takes file descriptor and data to write. |
| `wait()`     | Suspends a parent process until a child process completes.     |

### Examples of System Calls in Windows and Unix
| **Operation**      | **Windows**                                    | **Unix**                    |
|--------------------|------------------------------------------------|-----------------------------|
| Process Control     | `CreateProcess()`, `ExitProcess()`           | `fork()`, `exit()`, `wait()`|
| File Manipulation   | `CreateFile()`, `ReadFile()`, `WriteFile()` | `open()`, `read()`, `write()`|
| Device Management    | `ReadConsole()`, `WriteConsole()`            | `read()`, `write()`         |
| Information Maintenance| `GetCurrentProcessID()`, `SetTimer()`      | `getpid()`, `alarm()`       |
| Communication       | `CreatePipe()`, `MapViewOfFile()`            | `pipe()`, `shmget()`        |

### Conclusion
System calls are vital for resource management and communication between user applications and the operating system, providing essential functionality for executing tasks and managing system resources effectively.

--- 

