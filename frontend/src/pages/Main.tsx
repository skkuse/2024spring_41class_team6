import Topbar from '../components/Header';
import JavaDiffEditor from '../components/JavaDiffEditor';
import JavaEditor from '../components/JavaEditor';
import ExecutionResult from '../components/ExecutionResult';

function Main() {
  return (
    <div>
      <JavaEditor />
      <ExecutionResult />
    </div>
  );
}
export default Main;
