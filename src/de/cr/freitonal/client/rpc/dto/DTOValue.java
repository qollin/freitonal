package de.cr.freitonal.client.rpc.dto;

public interface DTOValue {

	DTOObject isObject();

	DTOArray isArray();

	DTONumber isNumber();

	DTOString isString();

}
