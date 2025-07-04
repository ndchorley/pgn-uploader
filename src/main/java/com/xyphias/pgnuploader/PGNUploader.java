import static java.lang.Integer.MAX_VALUE;

void main() throws IOException {
    var root = readln("Enter the root directory: ").trim();
    
    var paths = pathsToEventsAndGamesIn(root);

    var events = eventsFrom(paths);
    println(events);
}

private static List<Path> pathsToEventsAndGamesIn(String root) throws IOException {
    try (var paths = Files.walk(Path.of(root), MAX_VALUE, FileVisitOption.FOLLOW_LINKS)) {
        return paths
                .skip(1)
                .filter(path -> !path.toString().contains(".git"))
                .toList();
    }
}

private List<String> eventsFrom(List<Path> paths) {
    return paths
            .stream()
            .map(Path::toFile)
            .filter(File::isDirectory)
            .map(file -> eventName(file))
            .toList();
}

private String eventName(File file) {
    var parts = file.getAbsolutePath().split("/");
    
    return parts[parts.length - 1];
}
