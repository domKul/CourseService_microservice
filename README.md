### Course service for creating courses and enrolling students microservice

####  part (4) of microservices
* (1) [Eureka Server](https://github.com/domKul/EurekaServer_microservice)
* (2) [Gateway](https://github.com/domKul/Gateway_microservice)
* (3) [Notification service](https://github.com/domKul/Notification_microservice)
* (4) [Course service](https://github.com/domKul/CourseService_microservice)
* (5) [Student service](https://github.com/domKul/Students_microservice)
* 
## Running the Courses Server

To run the Courses Server, follow these steps:

1. Make sure you have Java installed on your machine.

2. Clone the project sources from the repository.

3. You need to setup the connection with mongoDB and setup rabbitmq queue connection in `application.properties` .

4. Run the program with IDE or use command `./mvnw spring-boot:run`

### Course Controller
This controller provides endpoints for managing courses in the system.

#### Find Courses
Endpoint: `GET /courses`
Description: Returns a list of courses.

Request Parameters:
+ name: (Optional) Filter the list by course name.

###### Response:
A list of courses in JSON format.
```
[
    {
        "code": "KOD_KURSU",
        "name": "Nazwa kursu",
        "description": "Opis kursu",
        "startData": "2023-12-15T20:00:00",
        "endData": "2023-12-30T22:30:00",
        "participantsLimit": 50,
        "participantsNumber": 1,
        "status": "ACTIVE",
        "courseMembersList": [
            {
                "localDateTime": "2023-12-18T17:50:03.16",
                "email": "jan.kowalski@example.com"
            }
        ]
    },
    {
        "code": "KOD_KURSU1",
        "name": "Nazwa kursu",
        "description": "Opis kursu",
        "startData": "2023-12-15T20:00:00",
        "endData": "2023-12-30T22:30:00",
        "participantsLimit": 1,
        "participantsNumber": 0,
        "status": "INACTIVE",
        "courseMembersList": []
    }
]
```

#### Find Course by Code
Endpoint: `GET /courses/{code}`

Description: Returns the course with the specified code.

Request Parameters:

+ code: The code of the course to retrieve.
###### Response:

```
{
        "code": "KOD_KURSU1",
        "name": "Nazwa kursu",
        "description": "Opis kursu",
        "startData": "2023-12-15T20:00:00",
        "endData": "2023-12-30T22:30:00",
        "participantsLimit": 1,
        "participantsNumber": 0,
        "status": "INACTIVE",
        "courseMembersList": []
    }
   ```

#### Create Course
Endpoint:` POST /courses`

Description: Creates a new course.

Request Body:
```
{
"code": "KOD_KURSU1",
"name": "Nazwa kursu",
"description": "Opis kursu",
"startData": "2023-12-15T20:00:00",
"endData": "2023-12-30T22:30:00",
"participantsLimit": 1,
"participantsNumber": 0,
"status": "INACTIVE",
"courseMembersList": []
}
```
###### Response:

The created course in JSON format.

#### Update Course
Endpoint: `PUT /courses/{code}`

Description: Updates the course with the specified code.

Request Parameters:

+ ode: The code of the course to modify.
Request Body:

JSON
```
{
"name": "Nazwa kursu",
"description": "Opis kursu",
"startData": "2023-12-15T20:00:00",
"endData": "2023-12-30T22:30:00",
"participantsLimit": 1,
"participantsNumber": 0,
"status": "INACTIVE",
"courseMembersList": []
}
```
###### Response:
The updated course in JSON format.

#### Patch Course
Endpoint: `PATCH /courses/{code}`

Description: Partially updates the course with the specified code.

Request Parameters:

+ code: The code of the course to modify.
Request Body:

JSON
```
{
"name": "Nazwa kursu",
"description": "Opis kursu",
"startData": "2023-12-15T20:00:00",
"endData": "2023-12-30T22:30:00",
"participantsLimit": 1,
"participantsNumber": 0,
"status": "INACTIVE",
"courseMembersList": []
}
```
###### Response:
The updated course in JSON format.

#### Enroll Student in Course
Endpoint: `POST /student/{studentId}/{courseCode}`

Description: Enrolls a student in a course.

Request Parameters:

+ studentId: The ID of the student to enroll.
+ courseCode: The code of the course to enroll in.
Response:

A status code indicating success or failure.

#### Get Students from Course by Code
Endpoint: `GET /courses/{code}/members`

Description: Returns a list of students enrolled in a course.

Request Parameters:
+ code: The code of the course to retrieve students from.

###### Response:

A list of student profiles in JSON format.

#### Finish Course Enrollment
Endpoint: `POST /{courseCode}/finish-enroll`

Description: Marks a course as finished for all enrolled students and send message to rabbitmq queue.


Request Parameters:
courseCode: The code of the course to finish enrollment for.
###### Response:
A status code indicating success or failure.