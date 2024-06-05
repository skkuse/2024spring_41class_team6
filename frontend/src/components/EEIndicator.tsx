import styled from 'styled-components';

type EEIndicatorProps = {
  type: 'co2' | 'energy';
  usage: number;
};

function EEIndicator({ type, usage }: EEIndicatorProps) {
  const header =
    type === 'co2'
      ? { title: '탄소 배출량', unit: 'gCO2e' }
      : { title: '전력 소모량', unit: 'kWh' };

  return (
    <EEWrapper>
      <EEHeader>
        {header.title}
        <span style={{ color: '#979797' }}>{header.unit}</span>
      </EEHeader>
      <EEBody type={type}>{usage.toFixed(2)}</EEBody>
    </EEWrapper>
  );
}

// style
const EEWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 4px;
  font-size: 16px;
`;
const EEHeader = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  width: 100%;
  margin: 0;
`;
const EEBody = styled.div<{ type: string }>`
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 16px 48px;
  font-size: 24px;
  border-radius: 16px;
  width: 100%;
  box-sizing: border-box;
  background-color: ${({ type }) => (type === 'co2' ? '#52C553' : '#FFC130')};
`;

export default EEIndicator;
