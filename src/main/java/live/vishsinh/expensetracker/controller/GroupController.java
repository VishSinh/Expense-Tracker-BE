package live.vishsinh.expensetracker.controller;

import live.vishsinh.expensetracker.entity.Group;
import live.vishsinh.expensetracker.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    public Iterable<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @PostMapping
    public Group createGroup(@RequestBody Group group) {
        return groupService.createGroup(group);
    }
}
