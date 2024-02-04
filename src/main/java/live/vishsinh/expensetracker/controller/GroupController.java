package live.vishsinh.expensetracker.controller;

import live.vishsinh.expensetracker.entity.Group;
import live.vishsinh.expensetracker.helpers.ResponseObj;
import live.vishsinh.expensetracker.helpers.VerifyUserToken;
import live.vishsinh.expensetracker.service.GroupService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private VerifyUserToken verifyUserToken;


    public static class GroupCreateRequest {
        public String adminId;
        public String password;
        public String groupName;
    }


    @PostMapping("/create")
    public ResponseObj createGroup(@RequestBody GroupCreateRequest requestBody, @RequestHeader("Authorization") String token) {
        try {
            boolean isTokenValid = verifyUserToken.verifyToken(token, requestBody.adminId);

            if (!isTokenValid) {
                return new ResponseObj(false, "Invalid Token", HttpStatus.UNAUTHORIZED);
            }

            if (requestBody.adminId == null || requestBody.password == null || requestBody.groupName == null) {
                throw new BadRequestException("Invalid request body. Please provide all required fields.");
            }

            Object newGroup = groupService.createGroup(requestBody.adminId, requestBody.password, requestBody.groupName);

            return new ResponseObj(true, "Group Created", newGroup, HttpStatus.CREATED);

        } catch (BadRequestException e) {
            return new ResponseObj(false, e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ResponseObj(false, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
