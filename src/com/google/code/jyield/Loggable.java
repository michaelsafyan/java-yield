// Michael Safyan; Copyright (C) 2010; Simplified BSD License.
package com.google.code.jyield;

/**
 * Provides classes with added logging capabilities and simplifies logging.
 * @author Michael Aaron Safyan
 */
public class Loggable
{
    /**
     * Default constructor.
     */
    public Loggable(){
        _logger = org.apache.log4j.Logger.getLogger(getClass());
    }

    /**
     * Reports the logger associated with this object.
     * @return The logger associated with this object.
     */
    protected org.apache.log4j.Logger getLogger(){
        return _logger;
    }

    /**
     * Logs a trace-level message.
     * @param message A message to be traced.
     */
    protected void trace(java.lang.Object message){
        _logger.trace(message);
    }

    /**
     * Logs a trace-level message, along with a Throwable object.
     * @param message A message to be traced.
     * @param error A throwable object to be traced.
     */
    protected void trace(java.lang.Object message, java.lang.Throwable error){
        _logger.trace(message,error);
    }

    /**
     * Logs a debug-level message.
     * @param message A message to be logged at the debug level.
     */
    protected void debug(java.lang.Object message){
        _logger.debug(message);
    }

    /**
     * Logs a debug-level message, along with a Throwable object.
     * @param message A message to be logged at the debug-level.
     * @param error A throwable object to be logged at the debug-level.
     */
    protected void debug(java.lang.Object message, java.lang.Throwable error){
        _logger.debug(message,error);
    }

    /**
     * Logs an info-level message.
     * @param message A message to be logged at the info level.
     */
    protected void info(java.lang.Object message){
        _logger.info(message);
    }

    /**
     * Logs an info-level message, along with a Throwable object.
     * @param message A message to be logged at the info-level.
     * @param error A throwable object to be logged at the info-level.
     */
    protected void info(java.lang.Object message, java.lang.Throwable error){
        _logger.info(message,error);
    }

    /**
     * Logs a warn-level message.
     * @param message A message to be logged at the warn level.
     */
    protected void warn(java.lang.Object message){
        _logger.warn(message);
    }
    
    /**
     * Logs a warn-level message, along with a Throwable object.
     * @param message A message to be logged at the warn-level.
     * @param error A throwable object to be logged at the warn-level.
     */
    protected void warn(java.lang.Object message, java.lang.Throwable error){
        _logger.warn(message,error);
    }

    /**
     * Logs an error-level message.
     * @param message A message to be logged at the error level.
     */
    protected void error(java.lang.Object message){
        _logger.error(message);
    }

    /**
     * Logs an error-level message, along with a Throwable object.
     * @param message A message to be logged at the error-level.
     * @param error A throwable object to be logged at the error-level.
     */
    protected void error(java.lang.Object message, java.lang.Throwable error){
        _logger.error(message,error);
    }

    /**
     * Logs a fatal-level message.
     * @param message A message to be logged at the fatal level.
     */
    protected void fatal(java.lang.Object message){
        _logger.fatal(message);
    }

    /**
     * Logs a fatal-level message, along with a Throwable object.
     * @param message A message to be logged at the fatal-level.
     * @param error A throwable object to be logged at the fatal-level.
     */
    protected void fatal(java.lang.Object message, java.lang.Throwable error){
        _logger.fatal(message,error);
    }

    /**
     * Use this function at the beginning of your function definitions
     * to log, at the trace level, entering the body of your function.
     * @param func The name of the function being entered.
     * @param params The parameters passed into the function being entered.
     */
    protected void entering(String func, java.lang.Object ... params){
        if ( _logger.isTraceEnabled() ){
            StringBuffer buffer = new StringBuffer("Entering : "+func+"(");
            if ( (params!=null) && (params.length>0) ){
                buffer.append(params[0].toString());
                for ( int i = 1; i< params.length; i++ ){
                    buffer.append(", "+((params[i]!=null)?(params[i].toString()):("null")));
                }
            }
            buffer.append(")");
            _logger.trace(buffer.toString());
        }
    }

    /**
     * Use this function at the end of your function definitions to log, at the
     * trace leevel, exiting the body of yoru function. Be sure to catch all
     * branches out -- including those resulting from throws and returns.
     *
     * @param func The name of the function being exited.
     */
    protected void exiting(String func){
        if ( _logger.isTraceEnabled() ){
            _logger.trace("Exiting  : "+func+"()");
        }
    }

    /**
     * Use this function at the end of your function definitions to log, at the
     * trace leevel, exiting the body of yoru function. Be sure to catch all
     * branches out -- including those resulting from throws and returns.
     *
     * @param func The name of the function being exited.
     * @param result The result being returned.
     */
    protected void exiting(String func, java.lang.Object result){
        if ( _logger.isTraceEnabled() ){
            _logger.trace("Exiting  : "+func+"() => "+((result!=null)?(result.toString()):("null")));
        }
    }

    private org.apache.log4j.Logger _logger = null;
}
