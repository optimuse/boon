package org.boon.json.implementation.serializers.impl;

import org.boon.core.Type;
import org.boon.core.reflection.FastStringUtils;
import org.boon.core.reflection.fields.FieldAccess;
import org.boon.json.JsonSerializer;
import org.boon.json.implementation.serializers.FieldSerializer;
import org.boon.primitive.CharBuf;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Created by rick on 1/1/14.
 */
public class FieldSerializerImpl implements FieldSerializer {


    private void serializeFieldName ( String name, CharBuf builder ) {
        builder.addJsonFieldName ( FastStringUtils.toCharArray ( name ) );
    }

    @Override
    public final boolean serializeField ( JsonSerializer serializer, Object parent, FieldAccess fieldAccess, CharBuf builder ) {

        final String fieldName = fieldAccess.getName ();
        final Type typeEnum = fieldAccess.typeEnum ();
        switch ( typeEnum ) {
            case INT:
                int value = fieldAccess.getInt ( parent );
                if (value !=0) {
                    serializeFieldName ( fieldName, builder );
                    builder.addInt ( value  );
                    return true;
                }
                return false;
            case BOOLEAN:
                boolean bvalue = fieldAccess.getBoolean ( parent );
                if ( bvalue ) {
                    serializeFieldName ( fieldName, builder );
                    builder.addBoolean ( bvalue  );
                    return true;
                }
                return false;

            case BYTE:
                byte byvalue = fieldAccess.getByte ( parent );
                if ( byvalue != 0 ) {
                    serializeFieldName ( fieldName, builder );
                    builder.addByte ( byvalue  );
                    return true;
                }
                return false;
            case LONG:
                long lvalue = fieldAccess.getLong ( parent );
                if ( lvalue != 0 ) {
                    serializeFieldName ( fieldName, builder );
                    builder.addLong ( lvalue  );
                    return true;
                }
                return false;
            case DOUBLE:
                double dvalue = fieldAccess.getDouble ( parent );
                if ( dvalue != 0 ) {
                    serializeFieldName ( fieldName, builder );
                    builder.addDouble ( dvalue  );
                    return true;
                }
                return false;
            case FLOAT:
                float fvalue = fieldAccess.getFloat ( parent );
                if ( fvalue != 0 ) {
                    serializeFieldName ( fieldName, builder );
                    builder.addFloat ( fvalue  );
                    return true;
                }
                return false;
            case SHORT:
                short svalue = fieldAccess.getShort( parent );
                if ( svalue != 0 ) {
                    serializeFieldName ( fieldName, builder );
                    builder.addShort ( svalue  );
                    return true;
                }
                return false;
            case CHAR:
                char cvalue = fieldAccess.getChar( parent );
                if ( cvalue != 0 ) {
                    serializeFieldName ( fieldName, builder );
                    builder.addChar( cvalue  );
                    return true;
                }
                return false;

        }

        Object value = fieldAccess.getObject ( parent );

        if ( value == null ) {
            return false;
        }

        /* Avoid back reference and infinite loops. */
        if ( value == parent ) {
            return false;
        }

        switch ( typeEnum )  {
            case BIG_DECIMAL:
                serializeFieldName ( fieldName, builder );
                builder.addBigDecimal ( (BigDecimal ) value );
                return true;
            case BIG_INT:
                serializeFieldName ( fieldName, builder );
                builder.addBigInteger ( ( BigInteger ) value );
                return true;
            case DATE:
                serializeFieldName ( fieldName, builder );
                serializer.serializeDate ( ( Date ) value, builder );
                return true;
            case STRING:
                serializeFieldName ( fieldName, builder );
                serializer.serializeString ( ( String ) value, builder );
                return true;
            case CHAR_SEQUENCE:
                serializeFieldName ( fieldName, builder );
                serializer.serializeString ( value.toString (), builder );
                return true;
            case INTEGER_WRAPPER:
                serializeFieldName ( fieldName, builder );
                builder.addInt ( ( Integer ) value );
                return true;
            case LONG_WRAPPER:
                serializeFieldName ( fieldName, builder );
                builder.addLong ( ( Long ) value );
                return true;
            case FLOAT_WRAPPER:
                serializeFieldName ( fieldName, builder );
                builder.addFloat ( ( Float ) value );
                return true;
            case DOUBLE_WRAPPER:
                serializeFieldName ( fieldName, builder );
                builder.addDouble ( ( Double ) value );
                return true;
            case SHORT_WRAPPER:
                serializeFieldName ( fieldName, builder );
                builder.addShort ( ( Short ) value );
                return true;
            case BYTE_WRAPPER:
                serializeFieldName ( fieldName, builder );
                builder.addByte ( ( Byte ) value );
                return true;
            case CHAR_WRAPPER:
                serializeFieldName ( fieldName, builder );
                builder.addChar ( ( Character ) value );
                return true;
            case ENUM:
                serializeFieldName ( fieldName, builder );
                builder.addQuoted ( value.toString () );
                return true;
            case COLLECTION:
            case LIST:
            case SET:
                Collection collection = (Collection) value;
                if ( collection.size () > 0) {
                    serializeFieldName ( fieldName, builder );
                    serializer.serializeCollection ( collection, builder );
                    return true;
                }
                return false;
            case MAP:
                Map map = (Map) value;
                if ( map.size () > 0) {
                    serializeFieldName ( fieldName, builder );
                    serializer.serializeMap ( map, builder );
                    return true;
                }
                return false;
            case ARRAY:
                Object []  array  = (Object []) value;
                if ( array.length > 0) {
                    serializeFieldName ( fieldName, builder );
                    serializer.serializeArray ( value, builder );
                    return true;
                }
                return false;
            default:
                serializeFieldName ( fieldName, builder );
                serializer.serializeInstance ( value, builder );
                return true;
        }

    }
}