package com.google.gson.mc626;

import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.stream.JsonToken;

import junit.framework.TestCase;

/** Test cases for JsonTreeReader, using control flow graph for test coverage **/
@SuppressWarnings("resource")
public class ControlFlowGraphTest extends TestCase {
    public void testJsonObject() throws IOException {
        JsonTreeReader objectInstance = new JsonTreeReader(new JsonObject());
        assertEquals(JsonToken.BEGIN_OBJECT, objectInstance.peek());
    }
    
    public void testJsonArray() throws IOException {
        JsonTreeReader objectInstance = new JsonTreeReader(new JsonArray());
        assertEquals(JsonToken.BEGIN_ARRAY, objectInstance.peek());
    }
    
    public void testJsonIterator() throws IOException {
        /* create an object */
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(1);
        jsonArray.add("2");
        
        JsonTreeReader iteratorInstance = new JsonTreeReader(jsonArray);
        iteratorInstance.beginArray();
        assertEquals(JsonToken.NUMBER, iteratorInstance.peek());
    }
    
    public void testJsonPrimitiveNumber() throws IOException {
        JsonTreeReader primitiveInstance = new JsonTreeReader(new JsonPrimitive(5));
        assertEquals(JsonToken.NUMBER, primitiveInstance.peek());
    }
    
    public void testJsonPrimitiveBoolean() throws IOException {
        JsonTreeReader primitiveInstance = new JsonTreeReader(new JsonPrimitive(true));
        assertEquals(JsonToken.BOOLEAN, primitiveInstance.peek());
    }
    
    public void testJsonPrimitiveString() throws IOException {
        JsonTreeReader primitiveInstance = new JsonTreeReader(new JsonPrimitive("string"));
        assertEquals(JsonToken.STRING, primitiveInstance.peek());
    }    

    public void testJsonNull() throws IOException {
        JsonTreeReader nullInstance = new JsonTreeReader(JsonNull.INSTANCE);
        assertEquals(JsonToken.NULL, nullInstance.peek());
    }
}
