/**
 * 
 */
package com.attilax.designpatter.commandPkg;

/**
 * @author ASIMO
 *
 */
public class Invoker {
    private Command command;
    public Invoker(Command command) {
        this.command = command;
    }
    public Object action() {
      return  command.execute();
    }
}