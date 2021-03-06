package eu.mhutti1.healthchain.constants;

/**
 * Created by nmatej1 on 28/11/2018.
 */
public class HealthRecordAttributesStandard implements HealthRecordAttributes {

    public static String schemaId = "8";

    public static String version = "1.0";

    public static String name = "health_record_standard";
    
    public static String attributes =
            "[\"title\"," +
                    "\"surname\"," +
//                    "\"active\"," +
//                    "\"name\"," +
//                    "\"gender\"," +
//                    "\"birthdate\"," +
//                    "\"deceased\"," +
//                    "\"address\"," +
//                    "\"maritalStatus\"," +
//                    "\"photo\"," +
//                    "\"contact\"," +
//                    "\"organization\"," +
//                    "\"communication_preference\"," +
//                    "\"activity_level\"," +
//                    "\"heart_rate\"," +
//                    "\"heart_pressure_sys\"," +
//                    "\"heart_pressure_dia\"," +
//                    "\"cholesterol\"," +
//                    "\"eye_sight\"," +
//                    "\"history_surgeries\"," +
//                    "\"list_medications\"," +
                    "\"age\"]";

    public static String[] attrs = new String[]{
//            "{\"name\": \"resource\", \"type\": \"string\"}",
//            "{\"name\": \"identifier\", \"type\": \"number\"}",
//            "{\"name\": \"active\", \"type\": \"boolean\"}",
//            "{\"name\": \"name\", \"type\": \"string\"}",
//            "{\"name\": \"gender\", \"type\": \"string\"}",
            "{\"name\": \"title\", \"type\": \"string\"}",
            "{\"name\": \"surname\", \"type\": \"string\"}",
//            "{\"name\": \"deceased\", \"type\": \"boolean\"}",
//            "{\"name\": \"address\", \"type\": \"string\"}",
//            "{\"name\": \"maritalStatus\", \"type\": \"string\"}",
//            "{\"name\": \"photo\", \"type\": \"string\"}",
//            "{\"name\": \"contact\", \"type\": \"string\"}",
//            "{\"name\": \"organization\", \"type\": \"string\"}",
//            "{\"name\": \"communication_preference\", \"type\": \"boolean\"}",
//            "{\"name\": \"activity_level\", \"type\": \"string\"}",
//            "{\"name\": \"heart_rate\", \"type\": \"number\", \"unit\": \"bpm\"}",
//            "{\"name\": \"heart_pressure_sys\", \"type\": \"number\", \"unit\": \"mmHg\"}",
//            "{\"name\": \"heart_pressure_dia\", \"type\": \"number\", \"unit\": \"mmHg\"}",
//            "{\"name\": \"cholesterol\", \"type\": \"number\", \"unit\": \"mg/dL\"}",
//            "{\"name\": \"eye_sight\", \"type\": \"number\", \"unit\": \"\"}",
//            "{\"name\": \"history_surgeries\", \"type\": \"string\"}",
//            "{\"name\": \"list_medications\", \"type\": \"string\"}",
            "{\"name\": \"age\", \"type\": \"number\"}"
    };
}