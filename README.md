# Solar Park Statistic POC Sample

This repository contains a sample  POC application. 
In the application, for the persistence layer Postgres DB is used. 
For the backend Spring 5 frameworks are used to expose statistic endpoints. 
For frontend Angular 2+ is used.

It consists of the following types:

### Back-end
| Class/Package/Path                   | Description                                   |
| ----------------------- | --------------------------------------------- |
| `vm.example.solarStatistic.SolarStatisticApplication`                | Contains a `main` method to start Back-end                  |
| `vm.example.solarStatistic.controller.SolarController`                | Controller, that expose two endpoints                   |
| `vm.example.solarStatistic.model`                | Model package, contains all models                  |
| `vm.example.solarStatistic.model.exception`                | Exceptions package, contains BadFormattedDateRangeException to reflect 'bad formatted' request                     |
| `vm.example.solarStatistic.model.solardb`                | Package for `DB` entities                   |
| `vm.example.solarStatistic.model.DayStatistic`                | POJO for reflecting day statistic                   |
| `vm.example.solarStatistic.repository`      | Package for `repository` entities             |
| `vm.example.solarStatistic.service` | Package for `service` entities    |
| `../src/main/resources/db/migration`         | Path to DB migration. Contains script to create all necessary DB tables  |
| `vm.example.solarStatistic.util.db`                | Package for embedded Postgres DB setup   |
| `HourlyProductionDataRepositoryTest`                | Contains `HourlyProductionDataRepository` tests  |
| `SolarServiceTest`                | Contains `SolarService` tests  |

### Front-end files located in ../src/main/frontend/solar
| Path                   | Description                                   |
| ----------------------- | --------------------------------------------- |
| `../src/app/app.component.html`                | Contains `main` html  |
| `../src/app/app.component.ts`                | Contains `main` ts. `Solar Parks` are requested |
| `../src/app/statistic-table`                | Folder for `Solar Park` statistic table component  |
| `../src/app/base-solar.service.ts`                | Contains `Base Solar Service`. Common functionality encapsulation  |
| `../src/app/solar-parks.service.ts`                | Service to retrieve `Solar Parks` data |
| `../src/app/solar-statistic.service.ts`                | Service to manage and retrieve `Solar Park Statistic` data  |


### Running the Back-end
 - You should had connection to Postgres DB and then adjust configuration in ../src/main/resources/application.yml
 ```yml
   datasource:
     url: jdbc:postgresql://localhost:{db-port}/{db-name}
     username: {user-name}
     password: {user-password}
 ```
 - Build using maven
 - Run the `vm.example.solarStatistic.SolarStatisticApplication` class

### Sample curl commands

Instead of running the client, here are some sample `curl` commands that access services exposed
by this sample:

```sh
#correct request body
curl -X POST -d '{"startDate":"2020-02-01T00:00:00.000Z", "endDate": "2020-03-04T12:00:00.000Z"}' -H "Content-Type: application/json" http://localhost:8080/solar/1/statistic

#incorrect request body
curl -X POST -d '{"startDate":"2020-03-05T00:00:00.000Z", "endDate": "2020-03-04T12:00:00.000Z"}' -H "Content-Type: application/json" http://localhost:8080/solar/1/statistic

```
### Running the Front-end
 - You should have angular installed on your machine
 - Navigate to ../solarStatistic/src/main/frontend/solar
 - Run command
```sh
ng serve 
```
