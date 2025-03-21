실습04-학생관리-step4 - Json 기반의 Repository 구현

* Json 기반의 file db형태로 repository 변경

dependency

pom.xml

* Jackson Databind
** https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
*** Jackson Databind 라이브러리는 내부적으로 Jackson Core, Jackson Annotation 의존성을 포함합니다.
* JSR310
** https://mvnrepository.com/artifact/com.fasterxml.jackson.datatype/jackson-datatype-jsr310
** LocalDateTime 대응하기 위해서 의존성을 추가합니다.

[source,xml]
----
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.14.2</version>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jsr310</artifactId>
    <version>2.14.2</version>
</dependency>

----

== Json

=== 직열화

* POJO 형태의 객체를 -&gt; JSON String으로 변환

=== 역직렬화

* JSON String -&gt; POJO 형태의 객체로 변환

=== JsonStudentRepository

* json파일 형태로 저장할 수 있도록 구현합니다.

[source,java]
----
public class JsonStudentRepository implements StudentRepository {
    private final ObjectMapper objectMapper;
    //json file 저장 경로
    private static final String JSON_FILE_PATH="/Users/nhn/IdeaProjects/servlet:jsp/student/src/main/json/student.json";

    public JsonStudentRepository(){
        objectMapper = new ObjectMapper();
        //LocalDatetime json 직열화/역직렬화 가능하도록 설정
        objectMapper.registerModule(new JavaTimeModule());
        //todo JSON_FILE_PATH 경로에 json 파일이 존재하면 삭제 합니다.

    }

    private synchronized List<Student> readJsonFile(){
        //todo json 파일이 존재하지 않다면 비어있는 List<Student> 리턴

        //json read & 역직렬화 ( json string -> Object )
        try(FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        ) {
            students = objectMapper.readValue(bufferedReader, new TypeReference<List<Student>>() {});
            return  students;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private synchronized void writeJsonFile(List<Student> studentList){
        // List<Student> 객체를 -> json 파일로 저장 : 직렬화
        File file = new File(JSON_FILE_PATH);

        try(
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {
            objectMapper.writeValue(bufferedWriter,studentList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Student student) {
        //json String -> Object 형태로 변화 (직렬화)
        List<Student> students = readJsonFile();
        //List에 student 추가
        students.add(student);
        //List<Student>객체를 -> json String 형태로 저장(직렬화)
        writeJsonFile(students);
    }
    //todo 나머지 method는 직접 구현하기
}
----

== WebApplicationListener

* MapStudentRepository -&gt; JsonStudentRepository 변경
