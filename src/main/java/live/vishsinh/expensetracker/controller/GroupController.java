package live.vishsinh.expensetracker.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import live.vishsinh.expensetracker.helpers.ResponseObj;
import live.vishsinh.expensetracker.helpers.VerifyUserToken;
import live.vishsinh.expensetracker.service.GroupService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.util.UUID;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private VerifyUserToken verifyUserToken;


    public static class GroupCreateRequest {
        @JsonProperty("user_id")
        public UUID userId;

        public String password;

        @JsonProperty("group_name")
        public String groupName;
    }


    @PostMapping("/create")
    public ResponseObj createGroup(@RequestBody GroupCreateRequest requestBody, @RequestHeader("Authorization") String token) {
        try {
            verifyUserToken.verifyToken(token, requestBody.userId.toString());

            if (requestBody.userId == null || requestBody.password == null || requestBody.groupName == null) {
                throw new BadRequestException("Invalid request body. Please provide all required fields.");
            }

            Object newGroup = groupService.createGroup(requestBody.userId, requestBody.password, requestBody.groupName);

            return new ResponseObj(true, "Group Created", newGroup, HttpStatus.CREATED);

        } catch (BadRequestException e) {
            return new ResponseObj(false, e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (LoginException e) {
            return new ResponseObj(false, e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ResponseObj(false, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
