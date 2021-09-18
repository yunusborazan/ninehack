package me.yagel15637.venture.command;

import me.yagel15637.venture.exceptions.VentureException;

public interface ICommand {
  String[] getAliases();
  
  String getDescription();
  
  String getUsage();
  
  void execute(String[] paramArrayOfString) throws VentureException;
}


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\yagel15637\venture\command\ICommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */