package com.example.taskapp.service;

import com.example.taskapp.model.Status;
import com.example.taskapp.model.Task;
import com.example.taskapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TaskServiceImpl implements TaskService{
    @Autowired
    private TaskRepository taskRepository;
    @Override
    public Set<Task> getTasks() {
        Set<Task> taskSet = new HashSet<>();
        taskRepository.findAll().iterator().forEachRemaining(taskSet::add);
        return taskSet;
    }
    @Override
    public Task findById(Long taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if(!taskOptional.isPresent()){
            throw new RuntimeException("Task Not Found!");
        }
        return taskOptional.get();
    }

    @Override
    public Task createTask(Task taskDetails) {
        Task newTask = taskRepository.save(taskDetails);
        return newTask;
    }

    @Override
    public Task updateTask(Long taskId, Task taskDetails) {
        Task task = findById(taskId);
        task.setTitle(taskDetails.getTitle());
        task.setContent(taskDetails.getContent());
        task.setStatus(taskDetails.getStatus());
        taskRepository.save(task);
        return task;
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.delete(findById(taskId));
    }

    @Override
    public void closeTask(Long taskId) {
        Task task = findById(taskId);
        task.closeTask();
        taskRepository.save(task);
    }

    @Override
    public void reopenTask(Long taskId) {
        Task task = findById(taskId);
        task.reopenTask();
        taskRepository.save(task);
    }

    @Override
    public Set<Task> getTasksByStatus(Status status) {
        Set<Task> allTasks = getTasks();
        Set<Task> filteredTasks = new HashSet<>();
        for(Task t : allTasks){
            if(t.getStatus().equals(status)){filteredTasks.add(t);}
        }
        return filteredTasks;
    }
}