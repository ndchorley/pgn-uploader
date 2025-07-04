import static java.lang.Integer.MAX_VALUE;

void main() throws IOException {
    var root = readln("Enter the root directory: ");
    
    try (var paths = pathsToEventsAndGamesIn(root)) {
        paths.forEach(path -> println(path.toString()));
    }
}

private static Stream<Path> pathsToEventsAndGamesIn(String root) throws IOException {
    return 
            Files.walk(Path.of(root), MAX_VALUE, FileVisitOption.FOLLOW_LINKS)
                    .skip(1)
                    .filter(path -> !path.toString().contains(".git"));
}
