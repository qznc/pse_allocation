// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package controller;

import data.Allocation;
import data.Student;
import data.Team;

/************************************************************/
/**
 * Konkretes Kommando zum verschieben eines Studierenden von seinem aktuellen Team in ein neues.
 */
public class MoveStudentCommand extends EditAllocationCommand {
	
	private Allocation allocation;
	private Student student;
	private Team newTeam;

	/**
	 * Erzeugt ein neues Kommando zum verschieben eines Studierenden.
	 * @param allocation Einteilung, auf die sich die Änderung bezieht.
	 * @param student Studierender, der verschoben werden soll.
	 * @param newTeam Neues Team, in das der Studierende eingeteilt wird.
	 */
	public MoveStudentCommand(Allocation allocation, Student student, Team newTeam) {
		super();
		this.allocation = allocation;
		this.student = student;
		this.newTeam = newTeam;
	}

	/**
	 * {@inheritDoc}
	 */
	public void execute() {
	}

	/**
	 * {@inheritDoc}
	 */
	public void undo() {
	}
}