package com.ralph.pmgr;

import picocli.CommandLine;

import java.util.List;

import static com.ralph.pmgr.utils.Utils.printJson;


@CommandLine.Command(name = "addPermission", description = "Add permissions.")
public class ConfluenceAddPermission implements Runnable {

    @CommandLine.Option(names = {"--userToAdd"},
            required = true,
            description = "Username you want to add permissions for.")
    String username;

    @CommandLine.Parameters(description = "Permissions you want to give to the User. Possible permissions are: " +
            "${COMPLETION-CANDIDATES}")
    List<ConfluencePermissionKeys> permissions;

    @CommandLine.ParentCommand
    private ConfluenceCli confluence;

    @Override
    public void run() {
        ConfluenceApi confluenceApi = null;
        try {
            confluenceApi = new ConfluenceApi(this.confluence.server);
            confluenceApi.login(this.confluence.user, this.confluence.password);
            boolean addPermissionsToSpace = confluenceApi.addPermissionsToSpace(permissions, username,
                    this.confluence.project);
            printJson(addPermissionsToSpace);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
