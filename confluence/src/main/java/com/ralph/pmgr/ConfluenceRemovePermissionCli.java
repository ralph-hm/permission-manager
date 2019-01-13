package com.ralph.pmgr;

import picocli.CommandLine;

import static com.ralph.pmgr.utils.Utils.printJson;


@CommandLine.Command(name = "removePermission", description = "Remove permissions.")
public class ConfluenceRemovePermissionCli implements Runnable {

    @CommandLine.Option(names = {"--userToRemove"},
            required = true,
            description = "Username you want to revoke permissions for.")
    String username;

    @CommandLine.Parameters(description = "Permissions you want to revoke to the User. Possible permissions are: " +
            "${COMPLETION-CANDIDATES}")
    ConfluencePermissionKeys permissions;

    @CommandLine.ParentCommand
    private ConfluenceCli confluence;

    @Override
    public void run() {
        ConfluenceApi confluenceApi = null;
        try {
            System.out.println(permissions);
            confluenceApi = new ConfluenceApi(this.confluence.server);
            confluenceApi.login(this.confluence.user, this.confluence.password);
            boolean removePermissionFromSpace = confluenceApi.removePermissionFromSpace(permissions.toString(),
                    username, this.confluence.project);
            printJson(removePermissionFromSpace);
            confluenceApi.logout();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
