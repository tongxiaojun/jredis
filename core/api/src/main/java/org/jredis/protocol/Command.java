/*
 *   Copyright 2009 Joubin Houshyar
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *    
 *   http://www.apache.org/licenses/LICENSE-2.0
 *    
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.jredis.protocol;

import org.jredis.Redis;




/**
 * Redis commands, verbatim. Each member of the Command enum maps to a 
 * corresponding (protocol level) command in Redis.  
 * 
 * <p><b>specification</b> <code>Redis 1.00</code>
 * 
 * @author  Joubin (alphazero@sensesay.net)
 * @version alpha.0, 04/02/09
 * @since   alpha.0
 * 
 */
@Redis(versions="1.1")
public enum Command {
	
	// security
	AUTH 		(RequestType.KEY, 			ResponseType.STATUS),
	
	// connection handling
	PING 		(RequestType.NO_ARG, 		ResponseType.STATUS), 
	QUIT 		(RequestType.NO_ARG, 		ResponseType.VIRTUAL), 

	// String values operations
	SET 		(RequestType.KEY_VALUE, 	ResponseType.STATUS), 
	GET 		(RequestType.KEY, 			ResponseType.BULK), 
	GETSET		(RequestType.KEY_VALUE, 	ResponseType.BULK), 
	MGET		(RequestType.MULTI_KEY, 	ResponseType.MULTI_BULK), 
	SETNX		(RequestType.KEY_VALUE, 	ResponseType.BOOLEAN),
	MSET		(RequestType.BULK_SET, 		ResponseType.STATUS), 
	MSETNX		(RequestType.BULK_SET, 		ResponseType.BOOLEAN), 
	INCR		(RequestType.KEY, 			ResponseType.NUMBER), 
	INCRBY		(RequestType.KEY_NUM,		ResponseType.NUMBER),  
	DECR		(RequestType.KEY, 			ResponseType.NUMBER), 
	DECRBY		(RequestType.KEY_NUM,		ResponseType.NUMBER),  
	EXISTS		(RequestType.KEY, 			ResponseType.BOOLEAN), 
	DEL			(RequestType.KEY, 			ResponseType.BOOLEAN), 
	TYPE		(RequestType.KEY, 			ResponseType.STRING),

	// "Commands operating on the key space"
	KEYS		(RequestType.KEY, 			ResponseType.BULK), 
	RANDOMKEY	(RequestType.NO_ARG,		ResponseType.STRING),
	RENAME		(RequestType.KEY_KEY, 		ResponseType.STATUS), 
	RENAMENX	(RequestType.KEY_KEY, 		ResponseType.BOOLEAN), 
	DBSIZE		(RequestType.NO_ARG,		ResponseType.NUMBER),
	EXPIRE		(RequestType.KEY_NUM,		ResponseType.BOOLEAN), 
	TTL			(RequestType.KEY,			ResponseType.NUMBER),
	
	// Commands operating on lists
	RPUSH		(RequestType.KEY_VALUE,		ResponseType.STATUS), 
	LPUSH		(RequestType.KEY_VALUE,		ResponseType.STATUS),
	LLEN		(RequestType.KEY,			ResponseType.NUMBER), 
	LRANGE		(RequestType.KEY_NUM_NUM,	ResponseType.MULTI_BULK), 
	LTRIM		(RequestType.KEY_NUM_NUM,	ResponseType.STATUS),
	LINDEX		(RequestType.KEY_NUM,		ResponseType.BULK), 
	LSET		(RequestType.KEY_IDX_VALUE,	ResponseType.STATUS), 
	LREM		(RequestType.KEY_CNT_VALUE,	ResponseType.NUMBER),
	LPOP		(RequestType.KEY,			ResponseType.BULK), 
	RPOP		(RequestType.KEY,			ResponseType.BULK),
	RPOPLPUSH	(RequestType.KEY_VALUE,		ResponseType.BULK),
	
	// Commands operating on sets
	SADD		(RequestType.KEY_VALUE,		ResponseType.BOOLEAN), 
	SREM		(RequestType.KEY_VALUE,		ResponseType.BOOLEAN), 
	SCARD		(RequestType.KEY,			ResponseType.NUMBER), 
	SISMEMBER	(RequestType.KEY_VALUE,		ResponseType.BOOLEAN), 
	SINTER		(RequestType.MULTI_KEY,		ResponseType.MULTI_BULK), 
	SINTERSTORE (RequestType.MULTI_KEY,		ResponseType.STATUS),
	SUNION		(RequestType.MULTI_KEY,		ResponseType.MULTI_BULK), 
	SUNIONSTORE (RequestType.MULTI_KEY,		ResponseType.STATUS), 
	SDIFF		(RequestType.MULTI_KEY,		ResponseType.MULTI_BULK), 
	SDIFFSTORE  (RequestType.MULTI_KEY,		ResponseType.STATUS),
	SMEMBERS	(RequestType.KEY,			ResponseType.MULTI_BULK), 
	SMOVE		(RequestType.KEY_KEY_VALUE,	ResponseType.BOOLEAN),
	SRANDMEMBER (RequestType.KEY,  			ResponseType.BULK),
	
	// Commands operating on sorted sets
	ZADD		(RequestType.KEY_IDX_VALUE,	ResponseType.BOOLEAN), 
	ZREM		(RequestType.KEY_VALUE,		ResponseType.BOOLEAN),
	ZCARD		(RequestType.KEY,			ResponseType.NUMBER), 
	ZSCORE		(RequestType.KEY_VALUE,		ResponseType.BULK),
	ZRANGE		(RequestType.KEY_NUM_NUM,	ResponseType.MULTI_BULK),
	ZREVRANGE	(RequestType.KEY_NUM_NUM,	ResponseType.MULTI_BULK),
	ZRANGEBYSCORE	(RequestType.KEY_NUM_NUM,	ResponseType.MULTI_BULK),
	ZINCRBY		(RequestType.KEY_IDX_VALUE, ResponseType.BULK),
		
	
	// "Multiple databases handling commands"
	SELECT		(RequestType.KEY,			ResponseType.STATUS),
	FLUSHDB		(RequestType.NO_ARG,		ResponseType.STATUS), 
	FLUSHALL	(RequestType.NO_ARG,		ResponseType.STATUS),
	MOVE		(RequestType.KEY_NUM,		ResponseType.BOOLEAN),
	
	// Sorting
	SORT		(RequestType.KEY_SPEC,		ResponseType.MULTI_BULK),
	
	// Persistence control commands
	SAVE		(RequestType.NO_ARG,		ResponseType.STATUS), 
	BGSAVE		(RequestType.NO_ARG,		ResponseType.STATUS), 
	LASTSAVE	(RequestType.NO_ARG,		ResponseType.NUMBER),
	SHUTDOWN	(RequestType.NO_ARG, 		ResponseType.VIRTUAL),
	
	// Remote server control commands
	INFO		(RequestType.NO_ARG, 		ResponseType.BULK), 
	MONITOR	    (RequestType.NO_ARG, 		ResponseType.VIRTUAL);
	
	/** semantic sugar */
	public final String code;
	public final byte[] bytes;
	public final int length;
//	public final int arg_cnt;
	public final RequestType requestType;
	public final ResponseType responseType;
	
	/**
	 * Each enum member directly corresponds to a Redis command, per
	 * specification.  Command semantics is specified by the element
	 * constructor params.
	 * @param reqType the {@link RequestType} of the Command
	 * @param respType the {@link ResponseType} of the Command
	 */
	Command (RequestType reqType, ResponseType respType) { 
		this.code = this.name(); 
		this.bytes = code.getBytes();
		this.length = code.length();
		this.requestType = reqType;
		this.responseType = respType;
//		this.arg_cnt = -1; // to raise exception -- make sure we don't miss any
	}

	// ------------------------------------------------------------------------
	// Inner Types
	// ------------------------------------------------------------------------

    /**
     * Broad Request Type categorization of the Redis Command per the request's
     * argument signature.  These categories are a more differentiated than the
     * Redis specification itself to impart further information about the argument
     * semantics.
	 *
     * @author  Joubin (alphazero@sensesay.net)
     * @version alpha.0, Aug 29, 2009
     * 
     */
    public enum RequestType {
    	/**  */
    	NO_ARG,
    	/**  */
    	KEY,
    	/**  */
    	KEY_KEY,
    	/**  */
    	KEY_NUM,
    	/**  */
    	KEY_SPEC,
    	/**  */
    	KEY_NUM_NUM,
    	/**  */
    	KEY_VALUE,
    	/**  */
    	KEY_KEY_VALUE,
    	/**  */
    	KEY_IDX_VALUE,
    	/**  */
    	KEY_CNT_VALUE,  // TODO: this should be key value cnt ...
    	/**  */
    	MULTI_KEY,
    	/**  */
    	BULK_SET
    }

    /**
     * Broad Response Type categorization of the Redis Command responses.
     * <p>
     * As with {@link RequestType}, there is further differentiation of the
     * Redis response types to further inform the semantics.
     * <p>
     * Beyond that, there is also linkage between the {@link ResponseType} and
     * its associated {@link Response} interface extension.
     * 
     * @author  Joubin (alphazero@sensesay.net)
     * @version alpha.0, Aug 29, 2009
     * @see Response
     */
    public enum ResponseType {
    	/**  */
    	VIRTUAL (StatusResponse.class),
    	/**  */
    	STATUS (StatusResponse.class),
    	/**  */
    	STRING (ValueResponse.class),
    	/**  */
    	BOOLEAN (ValueResponse.class),
    	/**  */
    	NUMBER (ValueResponse.class),
    	/**  */
    	BULK (BulkResponse.class),
    	/**  */
    	MULTI_BULK (MultiBulkResponse.class);
    	public Class<? extends Response> respClass;
    	
    	/**
    	 * For each {@link ResponseType} member, we specify the 
    	 * corresponding {@link Response} extension interface.
    	 * @param respClass
    	 */
    	ResponseType (Class<? extends Response> respClass){
    		this.respClass = respClass;
    	}
    }
}