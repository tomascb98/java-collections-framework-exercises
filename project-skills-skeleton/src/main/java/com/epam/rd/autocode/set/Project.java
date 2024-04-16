package com.epam.rd.autocode.set;

import java.util.*;

public class Project {
	private List<Role> roles;
	
	private static class Entry {
		private Enum<Level> level;
		private Enum<Skill> skill;

		public Entry(Enum<Level> level, Enum<Skill> skill) {
			this.level = level;
			this.skill = skill;
		}

		public Enum<Level> getLevel() {
			return level;
		}

		public Enum<Skill> getSkill() {
			return skill;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Entry entry)) return false;
			return Objects.equals(getLevel(), entry.getLevel()) && Objects.equals(getSkill(), entry.getSkill());
		}

		@Override
		public int hashCode() {
			return Objects.hash(getLevel(), getSkill());
		}
	}

	public Project(Role... roles) {
		this.roles=new LinkedList<>(List.of(roles));
	}

	public int getConformity(Set<Member> team) {
		List<Entry> projectEntries = new ArrayList<>();
		for (Role role: getRoles() ) {
			for (Skill skill:role.getSkills()) {
				projectEntries.add(new Entry(role.getLevel(), skill));
			}
		}
		int originalSize = projectEntries.size();

		List<Entry> teamEntries = new ArrayList<>();
		for(Member member: team){
			for (Skill skill : member.getSkills()) {
				teamEntries.add(new Entry(member.getLevel(), skill));
			}
		}
		Iterator<Entry> projectIterator = projectEntries.iterator();


		while(projectIterator.hasNext()){
			Entry projectEntry = projectIterator.next();
			Iterator<Entry> teamIterator = teamEntries.iterator();
			while(teamIterator.hasNext()){
				Entry teamEntry = teamIterator.next();
				if(teamEntry.equals(projectEntry) && projectEntry.hashCode() == teamEntry.hashCode()){
					projectIterator.remove();
					teamIterator.remove();
					break;
				}
			}
		}

        return (int) ((originalSize-projectEntries.size())*100.0)/originalSize;
	}

	public List<Role> getRoles() {
		return roles;
	}
}
