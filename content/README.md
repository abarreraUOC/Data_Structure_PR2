# PR2 - System Issues Management

## Author
- name: Abraham Barrera Herrera
- e-mail: abarrerah@uoc.edu

## Scope of the submission
This submission includes the complete implementation of the PR2 project for the `SystemIssues` ADT.

The delivered project includes:
- the implementation of `SystemIssuesPR2Impl`
- the implementation of the model classes:
  - `Worker`
  - `System`
  - `Component`
  - `Issue`
- the implementation of the custom exceptions required by the interface
- the implementation of the auxiliary stack structure needed by the project
- the provided test suite
- additional test cases created to improve coverage
- evidence of successful test execution

## Overview
This project implements a computer system issue management system that supports the administration of workers, systems, components, and issues.

The application allows:
- registering and updating workers, systems, and components
- installing components into systems
- creating issues linked to components
- assigning issues to workers using a LIFO policy
- solving assigned issues
- querying registered systems and relationships between entities
- retrieving the top worker and the system with the largest number of installed components

## Main Project Components

### 1. ADT Contract: `SystemIssues.java`
This interface defines the required operations of the issue management system, including:
- `addWorker(workerId, name, address)`
- `addSystem(systemId, description, location)`
- `addComponent(componentId, trademark, model, serial)`
- `installComponentToSystem(componentId, systemId)`
- `createIssue(issueId, componentId, description, dateTime)`
- `assignIssue(issueId, workerId)`
- `solveIssue(workerId)`
- `getSystems()`
- `getComponentsBySystem(systemId)`
- `getDoneIssuesByWorker(workerId)`
- `getTopWorker()`
- `getSystemWithMostComponents()`

It also includes helper access through `getSystemIssuesHelper()`.

### 2. Implementation: `SystemIssuesPR2Impl.java`
This class implements the `SystemIssues` interface and contains the core logic of the application.

Main responsibilities:
- storing all workers, systems, components, and issues
- managing the relationships between entities
- applying the LIFO rule for assigned issues
- maintaining the data required by the helper interface
- validating preconditions and throwing the proper exceptions

### 3. Helper Interface: `SystemIssuesHelper.java`
This interface provides support methods for inspection and validation:
- `getWorker(String id)`
- `numWorkers()`
- `getSystem(String id)`
- `numSystems()`
- `getComponent(String id)`
- `numComponents()`
- `numComponentsBySystem(String systemId)`
- `numIssues()`
- `numIssuesByComponent(String componentId)`
- `numIssuesByWorker(String workerId)`

### 4. Data Models
The following model classes are part of the implementation:
- `Worker.java`
- `System.java`
- `Component.java`
- `Issue.java`

### 5. Custom Exceptions
The following exceptions are implemented:
- `DSException`
- `ComponentNotFoundException`
- `ComponentAlreadyInstalledException`
- `IssueNotFoundException`
- `IssueAlreadyAssignedException`
- `IssueAlreadyResolvedException`
- `WorkerNotFoundException`
- `NoIssuesException`
- `NoWorkerException`
- `NoSystemsException`
- `SystemHasNoComponentsException`

## Modifications and/or updates over the initial PEC1 design
Compared with the initial PEC1 design, the implementation was adjusted to satisfy the constraints and expectations of the PR2 test suite.

### Design decisions
- The solution avoids prohibited `java.util` collection classes.
- Assigned issues are managed with a LIFO structure, as required by the statement and tests.
- Systems keep track of their installed components.
- Components keep track of their related issues.
- Workers keep track of both assigned and completed issues.

### Justification
These design decisions were necessary to:
- match the functional contract of the `SystemIssues` interface
- comply with the architectural restrictions checked by the provided tests
- support the required LIFO behavior
- keep the implementation aligned with the data structures studied in the course

## Provided test classes
The project includes the following provided tests:
- `ArchitectureTest.java`
- `FactorySystemIssues.java`
- `StackLinkedListTest.java`
- `SystemIssuesPR2Test.java`

These tests validate:
- entity creation and updates
- issue creation, assignment, and resolution
- LIFO behavior
- queries over systems, components, and completed issues
- architectural restrictions on forbidden Java collections

## Additional test cases
As requested in the PR2 statement, the test suite was extended with new scenarios.

### Added test classes
- `SystemIssuesExtraTest.java`
- `StackLinkedListExtraTest.java`

### Purpose of the new tests
The additional test classes were created to validate cases such as:
- consistency of links between related entities
- extra checks for the LIFO issue resolution order
- update operations without duplication
- validation of issue state after resolution
- extra checks for the system with the largest number of components
- additional validation of the custom linked stack behavior
- checks for empty stack state, push/pop behavior, and value iteration

## Problems encountered and additional comments
During development, the main challenge was implementing the project while respecting these constraints:
- not modifying the provided official test files
- not using prohibited `java.util` collection classes
- keeping the code compatible with the expected behavior of the official test suite


## Evidence of test execution
All JUnit tests under `src/test/java` were executed successfully in green.

Image file:
- `evidence/tests-all-green.png`

![Test execution evidence](evidence/tests-all-green.png)

## Final remarks
This submission follows the structure of the provided base project and implements the required functionality for the `SystemIssues` ADT.

The solution is designed to be compatible with the official tests and with the additional tests included in this submission.