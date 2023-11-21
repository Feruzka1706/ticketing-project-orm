package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    @Override
    public ProjectDTO getByProjectCode(String projectCode) {
        return null;
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        return null;
    }

    @Override
    public void save(ProjectDTO projectDTO) {

    }

    @Override
    public void update(ProjectDTO projectDTO) {

    }

    @Override
    public void deleteProject(String projectCode) {

    }
}
