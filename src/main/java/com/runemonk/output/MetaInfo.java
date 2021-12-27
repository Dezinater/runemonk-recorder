package com.runemonk.output;
import lombok.*;


@Data
public class MetaInfo {
    //version and source fields will be useful for external tools
    String version = "1.0.0";

    //might make a tool to modify/create these recording files
    //so it could be good to have the source program name
    String source = "runelite";
    String sourceVersion;

    String username;
    int world;
    long startTime;
}
