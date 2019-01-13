package com.ralph.pmgr;

import picocli.CommandLine;

@CommandLine.Command(mixinStandardHelpOptions = true,
        name = "confluence",
        subcommands = {ConfluenceShowPermissionCli.class, ConfluenceAddPermission.class, ConfluenceRemovePermissionCli.class})
public class ConfluenceCli implements Runnable {

    @CommandLine.Option(names = {"-u", "--user"}, description = "User name")
    String user;
    @CommandLine.Option(names = {"-p", "--password"}, description = "Passphrase")
    String password;
    @CommandLine.Option(names = {"-v", "--verbose"}, description = "Be verbose.")
    boolean verbose = false;
    @CommandLine.Option(names = {"-s", "--server"}, description = "Server name, e. g. https://www.confluence.com/")
    String server = "https://www.confluence.com/";
    @CommandLine.Option(names = {"--project"}, description = "Project key, e. g. ADAM")
    String project;

    public static void main(String[] args) {
        CommandLine.run(new ConfluenceCli(), System.err, args);
    }

    @Override
    public void run() {

    }
}
