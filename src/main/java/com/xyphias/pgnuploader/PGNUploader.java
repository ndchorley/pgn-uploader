import static java.lang.Integer.MAX_VALUE;

void main() throws IOException {
    var root = readln("Enter the root directory: ");
    
    try (var paths = Files.walk(Path.of(root), MAX_VALUE, FileVisitOption.FOLLOW_LINKS)) {
        paths
                .filter(path -> !path.toString().contains(".git"))
                .forEach(path -> println(path.toString()));
    } catch (Exception e) {
        throw e;
    }
}
