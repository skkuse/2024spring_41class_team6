import styled from 'styled-components';
import GitURLForm from '../components/GitURLForm';
import LoadingAirplane from '../components/LoadingAirplane';

function GitRepo() {
  return (
    <Wrapper>
      <GitURLForm />
    </Wrapper>
  );
}

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
`;

export default GitRepo;
