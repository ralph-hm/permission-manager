package com.ralph.pmgr;

import picocli.CommandLine;

@CommandLine.Command(mixinStandardHelpOptions = true,
        version = "checksum 3.0",
        subcommands = {ConfluenceCli.class})
public class AtlassianCli implements Runnable {

    public static void main(String[] args) {
        CommandLine.run(new AtlassianCli(), System.err, args);
    }

    @Override
    public void run() {

    }
}
