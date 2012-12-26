package br.com.globalcode.android.vo;

import java.io.Serializable;

public class Task implements Serializable {
	
	private static final long serialVersionUID = 6734638702431329595L;
	
	private final Integer id;
	private final String name;
	private final String description;
	private final Boolean finished;
	
	private Task(final Integer _id, final String _name, final String _description, final Boolean _finished) {
		
		this.id = _id;
		this.name = _name;
		this.description = _description;
		this.finished = _finished;
	}
	
	public static final Task getInstance(final Integer id, final String name, final String description, final Boolean finished) {
		
		return new Task(id, name, description, finished);
	}
	
	@Override
	public String toString() {
		
		return this.name;
	}

	public final Integer getId() {
		return id;
	}

	public final String getName() {
		return name;
	}

	public final String getDescription() {
		return description;
	}

	public final Boolean getFinished() {
		return finished;
	}

}
