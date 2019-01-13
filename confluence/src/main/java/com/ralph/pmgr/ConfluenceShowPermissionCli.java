package com.ralph.pmgr;

import picocli.CommandLine;

import java.util.ArrayList;

import static com.ralph.pmgr.utils.Utils.printJson;
import static com.ralph.pmgr.utils.Utils.toClass;


@CommandLine.Command(name = "showPermission", description = "Show the current permissions.")
public class ConfluenceShowPermissionCli implements Runnable {

    @CommandLine.Option(names = {"--userToAdd"}, description = "Username you want to show permissions for.")
    String username;

    @CommandLine.ParentCommand
    private ConfluenceCli confluence;

    @Override
    public void run() {
        ConfluenceApi confluenceApi = null;
        try {
            confluenceApi = new ConfluenceApi(this.confluence.server);
            confluenceApi.login(this.confluence.user, this.confluence.password);
            Object[] confluenceSpacePermissionSets = confluenceApi.getSpacePermissionSets(this.confluence.project);
            ArrayList<ConfluencePermissions> list = new ArrayList<>();
            for (Object confluenceSpacePermission : confluenceSpacePermissionSets) {
                ConfluencePermissions permissions = (ConfluencePermissions) toClass(confluenceSpacePermission,
                        ConfluencePermissions.class);
                list.add(permissions);
            }
            printJson(list);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
