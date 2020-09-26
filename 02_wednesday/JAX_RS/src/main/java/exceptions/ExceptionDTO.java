/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author rh
 */
public class ExceptionDTO {

    private int code;
    private String msg;

    public ExceptionDTO(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
}
