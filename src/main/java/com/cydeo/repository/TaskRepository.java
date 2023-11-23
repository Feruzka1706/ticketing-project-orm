package com.cydeo.repository;

import com.cydeo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task,Long> {

    //JPQL
    @Query("SELECT COUNT(task) FROM Task task WHERE task.project.projectCode = ?1 AND task.taskStatus <> 'Completed' ")
    int totalNonCompletedTasks( String projectCode);


    //Native Query
    @Query(value = "SELECT COUNT(*)" +
            " FROM tasks t JOIN projects p ON t.project_id = p.id" +
            " WHERE p.project_code = ?1 AND t.task_status = 'COMPLETE'",nativeQuery = true)
    int totalCompletedTasks(String projectCode);

}
