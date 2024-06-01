import Topbar from '../components/Header';
import JavaDiffEditor from '../components/JavaDiffEditor';
import JavaEditor from '../components/JavaEditor';
import ExecutionResult from '../components/ExecutionResult';
import styled from 'styled-components';

function Main() {
  return (
    <Wrapper>
      <JavaEditor />
      {/* <JavaDiffEditor /> */}
      <ExecutionResult />
    </Wrapper>
  );
}

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
`;
export default Main;
