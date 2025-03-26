package com.example.business.service;

import com.example.business.model.Task;
import com.example.business.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(int id) {
        return taskRepository.findById(id);
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(int id, Task taskDetails) {
        return taskRepository.findById(id).map(task -> {
            task.setDatumPocetka(taskDetails.getDatumPocetka());
            task.setDatumZavrsetka(taskDetails.getDatumZavrsetka());
            task.setDatumPredaje(taskDetails.getDatumPredaje());
            task.setNaziv(taskDetails.getNaziv());
            task.setOpis(taskDetails.getOpis());
            task.setStatus(taskDetails.getStatus());
            task.setProjekat(taskDetails.getProjekat());
            task.setKorisnik(taskDetails.getKorisnik());
            return taskRepository.save(task);
        }).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }
}

