import Topbar from '../components/Header';
import JavaDiffEditor from '../components/JavaDiffEditor';
import JavaEditor from '../components/JavaEditor';
import ExecutionResult from '../components/ExecutionResult';
import { useState } from 'react';

function Main() {
  const [isDiffEditor, setIsDiffEditor] = useState(false);

  if (isDiffEditor) {
    return (
      <div>
        <JavaDiffEditor setDiffEditor={setIsDiffEditor} />
        <ExecutionResult />
      </div>
    );
  } else {
    return (
      <div>
        <JavaEditor setDiffEditor={setIsDiffEditor} />
      </div>
    );
  }
}
export default Main;
