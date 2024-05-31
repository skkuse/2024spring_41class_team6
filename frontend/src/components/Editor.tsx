import styled from 'styled-components';
import React, { PureComponent, useState, MouseEvent } from 'react';
import ReactDiffViewer, { DiffMethod } from 'react-diff-viewer-continued';
import cn from 'classnames';
const StyledEditor = styled.div`
  width: 85%;
  margin: 0 auto;
`;

const StyledTopWrapper = styled.div`
  margin: 10px auto;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 0px 32px;
  height: 35px;
`;

const StyledCodeExplain = styled.p`
  font-family: 'Pretendard';
  font-style: normal;
  font-weight: 600;
  font-size: 20px;
  line-height: 24px;
  color: #3f51b5;
`;

const StyledRunButton = styled.button`
  font-size: 15px;
  font-weight: 600;
  padding: 8px 24px;
  background: #dedeff;
  border-radius: 8px;
  border: none;
`;

function Editor() {
  // 유저가 입력한 코드와 그린코드
  const [oldCode, setOldCode] = useState(`const a = 10
    const b = 10
    const c = () => console.log('foo')
    
    if(a > 10) {
      console.log('bar')
    }
    
    console.log('done')`);

  const [greenCode, setGreenCode] = useState(`const a = 10
    const boo = 10
    
    if(a === 10) {
      console.log('bar')
    }`);

  return (
    <StyledEditor>
      <StyledTopWrapper>
        <StyledCodeExplain>
          기존코드/그린코드화 패턴 적용 코드
        </StyledCodeExplain>
        <StyledRunButton>코드다시 입력하기</StyledRunButton>
      </StyledTopWrapper>
      <Diff oldCode={oldCode} greenCode={greenCode}></Diff>
    </StyledEditor>
  );
}

// 온라인 diff 에디터 https://github.com/aeolun/react-diff-viewer-continued

interface DiffViewProps {
  oldCode: string;
  greenCode: string;
  // splitView?: boolean;
  // highlightLine?: string[];
  // language?: string;
  // lineNumbers: boolean;
  // theme: 'dark' | 'light';
  // enableSyntaxHighlighting?: boolean;
  // columnHeaders: boolean;
  // compareMethod?: DiffMethod;
  // dataType: string;
  // customGutter?: boolean;
}

interface DiffViewState {
  // oldCode: string;
  // greenCode: string;
  splitView: boolean;
  highlightLine: string[];
  lineNumbers: boolean;
  theme: 'dark' | 'light';
  enableSyntaxHighlighting?: boolean;
  columnHeaders: boolean;
  compareMethod?: DiffMethod;
  dataType: string;
  customGutter?: boolean;
}

// for Syntax highlighting
const P = (window as any).Prism;

class Diff extends PureComponent<DiffViewProps, DiffViewState> {
  // enum DiffMethod {
  //   CHARS = 'diffChars',
  //   WORDS = 'diffWords',
  //   WORDS_WITH_SPACE = 'diffWordsWithSpace',
  //   LINES = 'diffLines',
  //   TRIMMED_LINES = 'diffTrimmedLines',
  //   SENTENCES = 'diffSentences',
  //   CSS = 'diffCss',
  // }
  public constructor(props: { oldCode: string; greenCode: string }) {
    super(props);
    this.state = {
      splitView: true,
      highlightLine: [],
      lineNumbers: true,
      theme: 'light',
      enableSyntaxHighlighting: true,
      columnHeaders: true,
      compareMethod: DiffMethod.WORDS_WITH_SPACE,
      dataType: 'javascript',
      customGutter: true,
    };
  }

  private onLineNumberClick = (
    id: string,
    e: MouseEvent<HTMLTableCellElement>
  ): void => {
    let highlightLine = [id];
    if (e.shiftKey && this.state.highlightLine.length === 1) {
      const [dir, oldId] = this.state.highlightLine[0].split('-');
      const [newDir, newId] = id.split('-');
      if (dir === newDir) {
        highlightLine = [];
        const lowEnd = Math.min(Number(oldId), Number(newId));
        const highEnd = Math.max(Number(oldId), Number(newId));
        for (let i = lowEnd; i <= highEnd; i++) {
          highlightLine.push(`${dir}-${i}`);
        }
      }
    }
    this.setState({
      highlightLine,
    });
  };

  // for Syntax highlighting
  // private highlightSyntax = (str: string): any => (
  //   <pre
  //     style={{ display: 'inline' }}
  //     dangerouslySetInnerHTML={{
  //       __html: P.highlight(str, P.languages.javascript),
  //     }}
  //   />
  // );
  private syntaxHighlight = (str: string): any => {
    if (!str) return;
    const language = P.highlight(str, P.languages.javascript);
    return <span dangerouslySetInnerHTML={{ __html: language }} />;
  };

  render() {
    return (
      <ReactDiffViewer
        highlightLines={this.state.highlightLine}
        onLineNumberClick={this.onLineNumberClick}
        alwaysShowLines={['L-30']}
        extraLinesSurroundingDiff={1}
        hideLineNumbers={!this.state.lineNumbers}
        oldValue={this.props.oldCode}
        compareMethod={this.state.compareMethod}
        splitView={this.state.splitView}
        newValue={this.props.greenCode}
        renderGutter={
          this.state.customGutter
            ? (diffData) => {
                return (
                  <td
                    className={
                      diffData.type !== undefined
                        ? cn(diffData.styles.gutter)
                        : cn(
                            diffData.styles.gutter,
                            diffData.styles.emptyGutter,
                            {}
                          )
                    }
                    title={'extra info'}
                  >
                    <pre className={cn(diffData.styles.lineNumber, {})}>
                      {diffData.type == 3
                        ? 'CHG'
                        : diffData.type == 2
                          ? 'DEL'
                          : diffData.type == 1
                            ? 'ADD'
                            : diffData.type
                              ? '==='
                              : undefined}
                    </pre>
                  </td>
                );
              }
            : undefined
        }
        renderContent={this.syntaxHighlight}
        useDarkTheme={this.state.theme === 'dark'}
        leftTitle={`master@2178133 - pushed 2 hours ago.`}
        rightTitle={`master@64207ee - pushed 13 hours ago.`}
      />
    );
  }
}

export default Editor;
