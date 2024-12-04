package controller;

import MyException.MyException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import model.programState.ProgramState;
import repository.*;

public class Controller {
    IRepository repo;
    Boolean displayFlag;
    ExecutorService executor;

    public Controller(IRepository _repo) {
        this.repo = _repo;
        this.displayFlag = false;
    }

    List<ProgramState> removeCompletedPrg(List<ProgramState> prgList) {
        return prgList.stream().filter(p -> p.isNotCompleted()).collect(Collectors.toList());
    }

    // public void fullExecution() throws MyException, InvalidOperation, IOException
    // {
    // ProgramState prgState = this.repo.getCurrentProgram();
    // this.repo.logProgramStateExecution();
    // while (!prgState.getExeStack().isEmpty()) {
    // if (this.displayFlag == true)
    // System.out.println(prgState);
    // this.oneStepExecution(prgState);
    // this.repo.logProgramStateExecution();
    // }
    // if (this.displayFlag == true)
    // System.out.println(prgState);
    // MyIHeap<Integer, Value> heap = prgState.getHeap();
    // heap.setHeap(heap.safeGarbageCollector(prgState.getUsedAddresses(),
    // heap.getHeap()));

    // }

    public void oneStepForAllPrg(List<ProgramState> prgList) throws InterruptedException {
        // before the execution we print the prgstate list into the log file
        prgList.forEach(prg -> {
            try {
                this.repo.logProgramStateExecution(prg);
            } catch (MyException | IOException e) {
                System.out.println(e.getMessage());
            }
        });

        List<Callable<ProgramState>> callList = prgList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (() -> {
                    return p.oneStepExecution();
                }))
                .collect(Collectors.toList());

        List<ProgramState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println(e.getMessage());
                    }
                    return null;
                }).filter(p -> p != null).collect(Collectors.toList());

        prgList.addAll(newPrgList); // toate statementurile returneaza null inafara de forkuri
        // si daca avem fork il adaugam cu addall u asta
        prgList.forEach(prg -> {
            try {
                this.repo.logProgramStateExecution(prg);
            } catch (MyException | IOException e) {
                System.out.println(e.getMessage());
            }
        });

        repo.setPrgList(prgList);
    }

    public void allStep() {
        this.executor = Executors.newFixedThreadPool(2);
        List<ProgramState> prgList = this.removeCompletedPrg(this.repo.getPrgList());
        while (!prgList.isEmpty()) {
            System.out.println("Number of active threads: " + Integer.toString(prgList.size()));
            try {
                this.oneStepForAllPrg(prgList);
                prgList = this.removeCompletedPrg(prgList);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        this.executor.shutdownNow();
        // here the repo still contains at least one completed program
        // because onestep calls setprglist so make sure to update it
        // just like this
        this.repo.setPrgList(prgList);
    }

    public void setDisplayFlag(Boolean _displayFlag) {
        this.displayFlag = _displayFlag;
    }

    public Boolean getDisplayFlag() {
        return this.displayFlag;
    }

    public void setLogFile(String logFile) {
        this.repo.setLogFile(logFile);
    }
}
