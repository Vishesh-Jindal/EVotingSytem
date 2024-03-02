package com.example.eVoting.constants;

public class Constants {
    public class QueryConstants {
        public final static String FETCH_BY_CANDIDATE_ELECTION = "SELECT ec FROM ElectionCandidateMapping ec WHERE ec.candidate.id=:candidateId AND ec.election.id=:electionId";
        public final static String FETCH_BY_ELECTION = "SELECT ec FROM ElectionCandidateMapping ec WHERE ec.election.id=:electionId";
        public final static String COUNT_BY_ELECTION_CANDIDATE_MAPPING = "SELECT COUNT(vec) FROM VoteElectionCandidateMapping vec WHERE vec.electionCandidateMapping.id=:electionCandidateMappingId";
    }
}
